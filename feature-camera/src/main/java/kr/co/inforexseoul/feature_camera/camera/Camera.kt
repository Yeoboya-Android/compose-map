package kr.co.inforexseoul.feature_camera.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.cameraPermissions
import kr.co.inforexseoul.feature_camera.R
import kr.co.inforexseoul.feature_camera.capture.takePicture
import kr.co.inforexseoul.feature_camera.extension.getCameraProvider

@Composable
fun CameraScreen() {
    CheckPermission(permissions = cameraPermissions) {
        val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
        CameraPreview(imageCapture)
    }
}

@Composable
fun CameraPreview(imageCapture: ImageCapture, lensFacing: Int = CameraSelector.LENS_FACING_FRONT) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val cameraSelector =
        CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val previewView = remember { PreviewView(context) }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
        CameraController(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalAlignment = Alignment.Bottom
        ) { action ->
            when (action) {
                CameraUIAction.OnImageCapture -> imageCapture.takePicture(context, lensFacing)
            }
        }
    }
}

@Composable
fun CameraController(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    cameraUIAction: (CameraUIAction) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment
    ) {
        IconButton(onClick = { cameraUIAction.invoke(CameraUIAction.OnImageCapture) }) {
            val capturePainter = painterResource(R.drawable.ic_baseline_photo_camera_55)
            Image(painter = capturePainter, contentDescription = "capture image")
        }
    }
}

sealed interface CameraUIAction {
    object OnImageCapture : CameraUIAction
}