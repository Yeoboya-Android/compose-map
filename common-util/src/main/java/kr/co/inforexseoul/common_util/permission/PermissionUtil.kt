package kr.co.inforexseoul.common_util.permission

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermission(permissions : List<String>, onGranted: @Composable () -> Unit = {}) {
    val permissionState = rememberMultiplePermissionsState(permissions)

    if (permissionState.allPermissionsGranted) {
        onGranted.invoke()
    } else {
        // TODO Alert 팝업 형식으로 바꿔야할 것 같음
        Column {
            val textToShow = if (permissionState.shouldShowRationale) {
                "필요한 권한이 허용되지 않음.(다시 물어볼수 있음)"
            } else {
                "필요한 권한이 허용되지 않았습니다. 권한을 허용해주세요. (다시 물어보기 불가)"
            }
            Text(textToShow)
            Button(onClick = { permissionState.launchMultiplePermissionRequest() }) {
                Text("권한 요청")
            }
        }
    }
}