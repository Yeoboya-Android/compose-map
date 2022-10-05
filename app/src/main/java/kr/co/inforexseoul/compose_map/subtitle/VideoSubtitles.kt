package kr.co.inforexseoul.compose_map.subtitle

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.MimeTypes
import com.google.common.collect.ImmutableList
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel
import kr.co.inforexseoul.common_util.ui.collectAsStateWithLifecycle
import kr.co.inforexseoul.compose_map.R
import kr.co.inforexseoul.core_data.state.Result

@Composable
fun VideoScreen(
    videoViewModel: VideoSubtitlesViewModel = viewModel(),
    appbarTitle: MutableState<@Composable () -> Unit>
) {

    /*val result by videoViewModel.clovaSubtitlesState.collectAsStateWithLifecycle(initial = Result.Loading)
    when (result) {
        is Result.Error -> {
            Log.e("qwe123", "subtitlesState error")
        }
        is Result.Loading -> {
            Log.i("qwe123", "subtitlesState loading")
        }
        is Result.Success -> {
            val data = (result as Result.Success<ClovaSpeechDataModel>).data
            Log.d("qwe123", "subtitlesState data size: ${data.segments.size}")
        }
    }*/

    val context = LocalContext.current
    appbarTitle.value = {
        Text(
            text = stringResource(id = R.string.drawer_title_vtt),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = (-32).dp)
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val exoplayer = remember(context) {
            ExoPlayer.Builder(context).build().apply {

                val srtUri = Uri.parse("file:///android_asset/sample_srt.srt")
                val subtitle = MediaItem.SubtitleConfiguration.Builder(srtUri)
                    .setMimeType(MimeTypes.APPLICATION_SUBRIP)
                    .setLanguage("ko")
                    .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
                    .build()

                val videoUri = Uri.parse("file:///android_asset/sample_video.mp4")
                val mediaItem = MediaItem.Builder()
                    .setUri(videoUri)
                    .setSubtitleConfigurations(ImmutableList.of(subtitle))
                    .build()

                setMediaItem(mediaItem)

                this.prepare()
            }
        }

        val exoplayer2 = remember(context) {
            ExoPlayer.Builder(context).build().apply {

                val srtUri = Uri.parse("file:///android_asset/sample_srt_2.srt")
                val subtitle = MediaItem.SubtitleConfiguration.Builder(srtUri)
                    .setMimeType(MimeTypes.APPLICATION_SUBRIP)
                    .setLanguage("ko")
                    .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
                    .build()

                val videoUri = Uri.parse("file:///android_asset/sample_video_2.mp4")
                val mediaItem = MediaItem.Builder()
                    .setUri(videoUri)
                    .setSubtitleConfigurations(ImmutableList.of(subtitle))
                    .build()

                setMediaItem(mediaItem)

                this.prepare()
            }
        }

        val exoplayer3 = remember(context) {
            ExoPlayer.Builder(context).build().apply {

                val srtUri = Uri.parse("file:///android_asset/sample_srt_3.srt")
                val subtitle = MediaItem.SubtitleConfiguration.Builder(srtUri)
                    .setMimeType(MimeTypes.APPLICATION_SUBRIP)
                    .setLanguage("ko")
                    .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
                    .build()

                val videoUri = Uri.parse("file:///android_asset/sample_video_3.mp4")
                val mediaItem = MediaItem.Builder()
                    .setUri(videoUri)
                    .setSubtitleConfigurations(ImmutableList.of(subtitle))
                    .build()

                setMediaItem(mediaItem)

                this.prepare()
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoplayer
                    }
                }
            )
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoplayer2
                    }
                }
            )
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoplayer3
                    }
                }
            )
        }
    }
}