package kr.co.inforexseoul.compose_map.subtitle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.MimeTypes
import com.google.common.collect.ImmutableList
import com.google.gson.Gson
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechSegment
import kr.co.inforexseoul.common_ui.UIConstants
import kr.co.inforexseoul.common_ui.component.LoadingBar
import kr.co.inforexseoul.common_ui.component.TextButton
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.storagePermissions
import kr.co.inforexseoul.compose_map.R
import org.json.JSONException
import java.io.*


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun VideoScreen(
    videoViewModel: VideoSubtitlesViewModel = viewModel(),
    appbarTitle: MutableState<@Composable () -> Unit>
) {
    CheckPermission(permissions = storagePermissions) {
        val context = LocalContext.current
        var videoUri by remember { mutableStateOf(Uri.parse("")) }
        var clovaData: ClovaSpeechDataModel? by remember { mutableStateOf(null) }
        val videoLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        getFile(context, uri)?.let {
                            videoUri = uri
                            videoViewModel.uploadVideo(it)
                        }
                    }
                }
            }
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "video/*"
            action = Intent.ACTION_PICK
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }

        appbarTitle.value = {
            TextButton(
                text = "동영상 선택 🎞",
                fontSize = UIConstants.FONT_SIZE_LARGE.sp,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .defaultMinSize(1.dp)
                    .fillMaxWidth()
                    .offset(x = (-32).dp),
                onClick = {
                    videoLauncher.launch(intent)
                }
            )
        }

        val result by videoViewModel.videoUploadState.collectAsStateWithLifecycle()
        when (result) {
            is SubtitleState.UnInit -> Unit
            is SubtitleState.Loading -> {
                clovaData = null
                LoadingBar()
            }
            is SubtitleState.Success -> {
                val data = (result as SubtitleState.Success).data
                clovaData = data
            }
        }

        if (clovaData != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                val srtUri = getSubtitleUri(context, clovaData!!)
                val exoPlayer = remember {
                    getExoplayer(
                        context = context,
                        videoUri = videoUri,
                        subtitleUri = srtUri
                    )
                }
                AndroidView(
                    modifier = Modifier.align(Alignment.Center),
                    factory = { PlayerView(it).apply { player = exoPlayer } }
                )
            }
        }
    }
}

@Composable
fun SampleVideoScreen(
    appbarTitle: MutableState<@Composable () -> Unit>
) {
    appbarTitle.value = {
        Text(
            text = stringResource(id = R.string.drawer_title_sample_video),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = (-32).dp)
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CheckPermission(permissions = storagePermissions) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.Center)
            ) {
                for (i in 1..3) {
                    AndroidView(
                        factory = { context ->
                            PlayerView(context).apply {

                                val videoUri = Uri.parse("file:///android_asset/sample_video_$i.mp4")
                                val srtUri = getSubtitleUri(context, "subtitle_$i.json")
                                player = getExoplayer(
                                    context = context,
                                    videoUri = videoUri,
                                    subtitleUri = srtUri
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun getExoplayer(
    context: Context,
    videoUri: Uri,
    subtitleUri: Uri
): ExoPlayer {
    return ExoPlayer.Builder(context).build().apply {
        val subtitle = MediaItem.SubtitleConfiguration.Builder(subtitleUri)
            .setMimeType(MimeTypes.APPLICATION_SUBRIP)
            .setLanguage("ko")
            .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
            .build()
        val mediaItem = MediaItem.Builder()
            .setUri(videoUri)
            .setSubtitleConfigurations(ImmutableList.of(subtitle))
            .build()
        setMediaItem(mediaItem)
        this.prepare()
    }
}

private fun getSubtitleUri(context: Context, jsonName: String): Uri {
    val assetManager = context.assets

    try {
        val jsonString = assetManager.open(jsonName).reader().readText()
        val data = Gson().fromJson(jsonString, ClovaSpeechDataModel::class.java)

        return getSubtitleUri(context, data)
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return Uri.parse("")
}

private fun getSubtitleUri(context: Context, data: ClovaSpeechDataModel): Uri {
    var srtString = ""
    data.segments.forEachIndexed { index, segment ->
        val appendStr = getSrtText(index, segment)
        srtString += appendStr
    }

    try {
        val fileName = "str_${data.token}.srt"
        val root = File(context.cacheDir, "strs")
        if (!root.exists()) {
            root.mkdirs()
        }
        val srtfile = File(root, fileName)
        val writer = FileWriter(srtfile)
        writer.append(srtString)
        writer.flush()
        writer.close()

        return srtfile.toUri()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return Uri.parse("")
}

private fun getSrtText(index: Int, segment: ClovaSpeechSegment) =
"""${index+1}
${getSrtTime(segment.start, segment.end)}
${segment.text}

"""

private fun getSrtTime(start: Long, end: Long): String {
    val startTime = String.format(
        "%02d:%02d:%02d,%03d",
        (start/ (1000*60*60)) % 24,
        (start/ (1000*60)) % 60,
        (start/ 1000) % 60,
        start % 1000
    )
    val endTime = String.format(
        "%02d:%02d:%02d,%03d",
        (end/ (1000*60*60)) % 24,
        (end/ (1000*60)) % 60,
        (end/ 1000) % 60,
        end % 1000
    )

    return "$startTime --> $endTime"
}

fun getFile(context: Context, uri: Uri): File? {
    var file: File? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        uri.path?.let { path->
            when(uri.scheme) {
                "file" -> file = File(path)
                "content" -> {
                    file = File(context.filesDir.path + File.separatorChar + getFileDisplayName(context, uri))
                    try {
                        context.contentResolver.openInputStream(uri).use { ins ->
                            ins?.let { createFileFromStream(it, file!!) }
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
                else -> {}
            }
        }
    } else {
        val path = getPathFromUri(context, uri)
        file = File(path)
    }

    return file?.takeIf { it.exists() }
}

private fun createFileFromStream(ins: InputStream, file: File) {
    try {
        FileOutputStream(file).use { os ->
            val buffer = ByteArray(4096)
            var length: Int
            while (ins.read(buffer).also { length = it } > 0) {
                os.write(buffer, 0, length)
            }
            os.flush()

            os.close()
            ins.close()
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun getPathFromUri(context: Context, uri: Uri): String {
    var result: String? = uri.path

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        when(uri.scheme) {
            "file" -> result = uri.path
            "content" -> getFile(context, uri)?.let { result = it.path }
        }
    } else {
        context.contentResolver.query(uri, null, null, null, null)?.apply {
            moveToFirst()
            getColumnIndex(MediaStore.Video.Media.DATA).let { result = if(!isNull(it)) getString(it) else "" }
            close()
        }
    }

    return result ?: ""
}

// 파일명 가져오기
fun getFileDisplayName(context: Context, uri: Uri): String {
    var name = ""

    context.contentResolver.query(uri, null, null, null, null)?.apply {
        moveToFirst()
        getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME).let { name = if(!isNull(it)) getString(it) else "" }
        close()
    }

    return name
}