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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import kr.co.inforexseoul.common_ui.theme.Compose_mapTheme
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.LOCATION_PERMISSIONS
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
                    SetMarkers()
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
        val coroutineScope = rememberCoroutineScope()
        ZoomControls(
            onZoomOut = {
                coroutineScope.launch {
                    cameraPositionState.animate(CameraUpdateFactory.zoomOut())
                }
            },
            onZoomIn = {
                coroutineScope.launch {
                    cameraPositionState.animate(CameraUpdateFactory.zoomIn())
                }
            }
        )
    }

}


@Composable
private fun MapButton(
    text : String,
    onClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Composable
private fun ZoomControls(
    onZoomOut : () -> Unit,
    onZoomIn : () -> Unit,
){
  MapButton("-", onClick = { onZoomOut() })
  MapButton("+", onClick = { onZoomIn() })
}

/**
 * 목록으로 마커 찍기
 */
@Composable
private fun SetMarkers() {
    val cameraList = listOf( Pair(35.15, 126.85), Pair(35.16, 126.86), Pair(35.15, 126.83) )
    cameraList.forEach { point ->
        Marker(
            state = rememberMarkerState(position = LatLng(point.first, point.second))
        )
    }
}

@Composable
private fun GetPresentLocation(reqLastLocation : Boolean, mapViewModel: MapViewModel, called : () -> Unit) {
    if(reqLastLocation){
        CheckPermission(permissions = LOCATION_PERMISSIONS) {
            mapViewModel.requestLocation()
            getCameraPosition(mapViewModel.presentLocation)
            called.invoke()
        }
    }
}

private fun getCameraPosition(location : Pair<Double, Double>) : CameraPosition {
    return CameraPosition.fromLatLngZoom(LatLng(location.first, location.second), 15f)
}