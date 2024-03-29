package kr.co.inforexseoul.compose_map.map.naver

import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.*
import com.naver.maps.map.overlay.OverlayImage
import kr.co.inforexseoul.common_ui.theme.MainTheme
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.locationPermissions
import kr.co.inforexseoul.compose_map.R
import kr.co.inforexseoul.compose_map.map.CameraPositionWrapper
import kr.co.inforexseoul.compose_map.map.MapState
import kr.co.inforexseoul.compose_map.map.MapViewModel
import kr.co.inforexseoul.compose_map.weather.WeatherView

private const val TAG = "NaverMap"

/**
 * 네이버 지도
 */
@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun OpenNaverMap(mapViewModel: MapViewModel = viewModel()) {
    MainTheme {

        var isMapLoaded by remember { mutableStateOf(false) }
        var reqLastLocation by remember { mutableStateOf(false) }
        val cameraPosition by mapViewModel.cameraPositionState.collectAsStateWithLifecycle()
        val cameraPositionState: CameraPositionState = rememberCameraPositionState {
                position = mapViewModel.getNaverCameraPosition(mapViewModel.presentLocation)
        }

        if (cameraPosition is CameraPositionWrapper.Naver)
            cameraPositionState.position = (cameraPosition as CameraPositionWrapper.Naver).cameraPosition

        Log.d("suk", "cameraPositionState: $cameraPositionState")

        Box(Modifier.fillMaxSize()) {
            NaverMapView(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    isMapLoaded = true
                },
                content = {
//                    GetMarkerInCameraBound(cameraPositionState, mapViewModel)
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
        GetPresentLocation(reqLastLocation, mapViewModel) { reqLastLocation = false }
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

    val coordinate: MutableState<Pair<Double, Double>?> = remember { mutableStateOf(null) }
    NaverMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapType = MapType.Basic),
        uiSettings = MapUiSettings(isCompassEnabled = false),
        onMapLoaded = onMapLoaded,
        onMapClick = { lat, lng->
            Log.d(TAG, "Position ${lat}, and ${lng}")
        },
        onMapLongClick = { _, latLng->
            coordinate.value = Pair(latLng.latitude, latLng.longitude)
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
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.background),
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(R.drawable.my_location),
                contentDescription = "presentLocation",
                tint = MaterialTheme.colors.onBackground
            )
        }
    }

    WeatherView(
        isGoogleMap = false,
        coordinate = coordinate
    )

}

/**
 * 현재 위치 가져오기
 */
@Composable
private fun GetPresentLocation(
    reqLastLocation: Boolean,
    mapViewModel: MapViewModel,
    called: () -> Unit
) {
    if (reqLastLocation) {
        CheckPermission(permissions = locationPermissions) {
            mapViewModel.requestLocation()
            mapViewModel.setCameraPositionState(MapState.NaverMap, mapViewModel.presentLocation)
            called.invoke()
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