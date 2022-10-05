package kr.co.inforexseoul.feature_camera.capture

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.webkit.MimeTypeMap
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.impl.ImageAnalysisConfig
import androidx.camera.core.impl.OptionsBundle
import androidx.core.net.toFile
import kr.co.inforexseoul.feature_camera.camera.Mirror
import kr.co.inforexseoul.feature_camera.extension.getOutputDirectory
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
const val PHOTO_EXTENSION = ".jpg"

internal fun ImageCapture.takePicture(
    context: Context,
    lensFacing: Int,
    onError: (ImageCaptureException) -> Unit = {},
    onImageCaptured: (Uri, Boolean) -> Unit = { _, _ -> },
) {
    val outputDirectory = context.getOutputDirectory()
    val photoFile = createFile(outputDirectory, FILENAME, PHOTO_EXTENSION)
    val outputFileOptions = getOutputFileOptions(lensFacing, photoFile)
    val imageAnalysis = ImageAnalysis.Builder().build().apply {
        setAnalyzer(Executors.newSingleThreadExecutor(), Mirror())
    }

    takePicture(
        outputFileOptions,
        Executors.newSingleThreadExecutor(),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
                val mimetype =
                    MimeTypeMap.getSingleton().getMimeTypeFromExtension(savedUri.toFile().extension)
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(savedUri.toFile().absolutePath),
                    arrayOf(mimetype)
                ) { _, uri -> }
                onImageCaptured(savedUri, false)
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }
    )
}

fun getOutputFileOptions(
    lensFacing: Int,
    photoFile: File
): ImageCapture.OutputFileOptions {
    val metadata = ImageCapture.Metadata().apply {
//        isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
    }
    return ImageCapture.OutputFileOptions.Builder(photoFile)
        .setMetadata(metadata)
        .build()
}

private fun createFile(baseFolder: File, format: String, extension: String) =
    File(
        baseFolder,
        SimpleDateFormat(format, Locale.KOREA).format(System.currentTimeMillis()) + extension
    )