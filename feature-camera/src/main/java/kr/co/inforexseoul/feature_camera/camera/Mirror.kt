package kr.co.inforexseoul.feature_camera.camera

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.media.Image
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

/** TODO */
class Mirror : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val image = imageProxy.image
        if (image != null && image.format == ImageFormat.YUV_420_888) {
            image.bitmap.mirror()
        }
        imageProxy.close()
    }

    private val Image.bitmap: Bitmap get() {
        val buffer = planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun Bitmap.mirror(): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(90f)

        val bitmap = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)

        val mirrorY = floatArrayOf(
            -1f, 0f, 0f,
            0f, 1f, 0f,
            0f, 0f, 1f
        )
        val newMatrix = Matrix()
        matrix.setValues(mirrorY)

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, newMatrix, true)
    }

}