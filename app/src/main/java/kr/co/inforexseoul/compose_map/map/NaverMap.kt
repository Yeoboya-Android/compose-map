package kr.co.inforexseoul.compose_map.map

import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.*
import com.naver.maps.map.overlay.OverlayImage
import kr.co.inforexseoul.common_ui.theme.MainTheme
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.locationPermissions
import kr.co.inforexseoul.compose_map.R

private const val TAG = "NaverMap"

/**
 * 네이버 지도
 */
@Composable
fun OpenNaverMap(mapViewModel: MapViewModel = viewModel()) {
    MainTheme {

        var isMapLoaded by remember { mutableStateOf(false) }
        var reqLastLocation by remember { mutableStateOf(false) }
        val cameraPositionState : CameraPositionState = rememberCameraPositionState {
            position = getCameraPosition(mapViewModel.presentLocation)
        }

        Box(Modifier.fillMaxSize()) {
            NaverMapView(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    isMapLoaded = true
                },
                content = {
                    GetMarkerInCameraBound(cameraPositionState, mapViewModel)
                }
            ){
                reqLastLocation = true
            }
            if(!isMapLoaded) {
                AnimatedVisibility(
                    modifier = Modifier.matchParentSize(),
                    visible = !isMapLoaded,
                    enter = EnterTransition.None,
                    exit = fadeOut()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .wrapContentSize()
                    )
                }
            }
        }
        GetPresentLocation(reqLastLocation, mapViewModel) {
            cameraPositionState.position = it
            reqLastLocation = false
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun NaverMapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded : () -> Unit = {},
    content : @Composable () -> Unit = {},
    onClick : () -> Unit = {}
) {
    NaverMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapType = MapType.Basic),
        uiSettings = MapUiSettings(isCompassEnabled = false),
        onMapLoaded = onMapLoaded,
        onMapClick = { lat, lng->
            Log.d(TAG, "Position ${lat}, and ${lng}")
        }
    ){
        content()
    }
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.padding(10.dp)
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(R.drawable.my_location),
                contentDescription = "presentLocation"
            )
        }
    }

}

/**
 * 현재 위치 가져오기
 */
@Composable
private fun GetPresentLocation(reqLastLocation : Boolean, mapViewModel: MapViewModel, called : (CameraPosition) -> Unit) {
    if(reqLastLocation) {
        CheckPermission(permissions = locationPermissions) {
            mapViewModel.requestLocation()
            called.invoke(getCameraPosition(mapViewModel.presentLocation))
        }
    }
}

/**
 * 카메라 범위안에 있는 곳에 마커 찍기
 */
@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun GetMarkerInCameraBound(cameraPositionState: CameraPositionState, mapViewModel : MapViewModel){
    if(!cameraPositionState.isMoving) {
        val context = LocalContext.current
        val bitmapDrawable = AppCompatResources.getDrawable(context, R.drawable.bus_station)?.toBitmap()
        mapViewModel.stationMap.forEach{
            val position = LatLng(it.value.first, it.value.second)
            if(cameraPositionState.coveringBounds?.contains(position) == true) {
                Marker(
                    state = rememberMarkerState(position = position),
                    icon = OverlayImage.fromBitmap(bitmapDrawable!!)
                )
            }
        }
    }
}


private fun getCameraPosition(location : Pair<Double, Double>) : CameraPosition {
    return CameraPosition(LatLng(location.first, location.second), 15.0)
}