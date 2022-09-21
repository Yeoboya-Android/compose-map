package kr.co.inforexseoul.compose_map.map

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kr.co.inforexseoul.common_ui.theme.Compose_mapTheme
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.locationPermissions
import kr.co.inforexseoul.compose_map.R

private const val TAG = "GoogleMap"

/**
 * 구글 지도
 */
@Composable
fun OpenGoogleMap(mapViewModel: MapViewModel = viewModel()) {
    Compose_mapTheme {
        var isMapLoaded by remember { mutableStateOf(false) }
        var reqLastLocation by remember { mutableStateOf(false) }
        val cameraPositionState = rememberCameraPositionState{
            position = getCameraPosition(mapViewModel.presentLocation)
        }

        Box(Modifier.fillMaxSize()){
            GoogleMapView(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    isMapLoaded = true
                },
                content = {
                    GetMarkerInCameraBound(cameraPositionState = cameraPositionState, mapViewModel = mapViewModel)
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


@Composable
private fun GoogleMapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded : () -> Unit = {},
    content : @Composable () -> Unit = {},
    onClick: () -> Unit = {}
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapType = MapType.NORMAL),
        uiSettings = MapUiSettings(compassEnabled = false),
        onMapLoaded = onMapLoaded,
        onPOIClick = {
            Log.d(TAG, "POI clicked: ${it.name}")
        },
        onMapClick = {
            Log.d(TAG, "Position ${it.latitude}, and ${it.longitude}")
        }
    ) {
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
    if(reqLastLocation){
        CheckPermission(permissions = locationPermissions) {
            mapViewModel.requestLocation()
            called.invoke(getCameraPosition(mapViewModel.presentLocation))
        }
    }
}

/**
 * 카메라 범위안에 있는 곳에 마커 찍기
 */
@Composable
private fun GetMarkerInCameraBound(cameraPositionState: CameraPositionState, mapViewModel: MapViewModel) {
    if(!cameraPositionState.isMoving) {
        mapViewModel.stationMap.forEach {
            val position = LatLng(it.value.first, it.value.second)
            if(cameraPositionState.projection!!.visibleRegion.latLngBounds.contains(position)) {
                Marker(rememberMarkerState(position = position))
            }
        }
    }


}

private fun getCameraPosition(location : Pair<Double, Double>) : CameraPosition {
    return CameraPosition.fromLatLngZoom(LatLng(location.first, location.second), 15f)
}