package kr.co.inforexseoul.compose_map.map.google

import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.*
import kr.co.inforexseoul.common_ui.theme.MainTheme
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.locationPermissions
import kr.co.inforexseoul.common_util.ui.collectAsStateWithLifecycle
import kr.co.inforexseoul.compose_map.R
import kr.co.inforexseoul.compose_map.map.MapViewModel
import kr.co.inforexseoul.compose_map.weather.WeatherView

private const val TAG = "GoogleMap"

/**
 * 구글 지도
 */
@Composable
fun OpenGoogleMap(
    mapViewModel: MapViewModel = viewModel(),
    stateHolder : GoogleMapStateHolder = rememberGoogleMapState(
        isMapLoaded = false,
        reqLastLocation = false,
        clusterData = ClusterData(LatLng(0.0, 0.0), "", ""),
        cameraPositionState = CameraPositionState(position = mapViewModel.getCameraPosition(mapViewModel.presentLocation))
    )
) {
    val position by mapViewModel.cameraPositionState.collectAsStateWithLifecycle(
        initial = mapViewModel.getCameraPosition(
            mapViewModel.presentLocation
        )
    )

    MainTheme {
        GetAllMarker(mapViewModel = mapViewModel, items = stateHolder.items)

        Box(Modifier.fillMaxSize()){
            GoogleMapView(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = stateHolder.cameraPositionState,
                onMapLoaded = {
                    stateHolder.isMapLoaded = true
                },
                content = {
                    //GetMarkerInCameraBound(cameraPositionState = cameraPositionState, mapViewModel = mapViewModel)
                    MapClustering(cameraPositionState = stateHolder.cameraPositionState, items = stateHolder.items)
                }
            ){
                stateHolder.reqLastLocation = true
            }
            if(!stateHolder.isMapLoaded) {
                AnimatedVisibility(
                    modifier = Modifier.matchParentSize(),
                    visible = !stateHolder.isMapLoaded,
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

        GetPresentLocation(
            stateHolder.reqLastLocation,
            mapViewModel
        ) { stateHolder.reqLastLocation = false }
        stateHolder.cameraPositionState.position = position
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

    val coordinate: MutableState<Pair<Double, Double>?> = remember { mutableStateOf(null) }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapType = MapType.NORMAL),
        uiSettings = MapUiSettings(compassEnabled = false),
        onMapLoaded = onMapLoaded,
        onPOIClick = {
            Log.d(TAG, "POI clicked: ${it.name}")
        },
        onMapLongClick = {
            Log.d(TAG, "Position ${it.latitude}, and ${it.longitude}")
            coordinate.value = Pair(it.latitude, it.longitude)
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
    WeatherView(
        isGoogleMap = true,
        coordinate = coordinate
    )
}

/**
 * 현재 위치 가져오기
 */
@Composable
private fun GetPresentLocation(reqLastLocation : Boolean, mapViewModel: MapViewModel, called: () -> Unit) {
    if(reqLastLocation){
        CheckPermission(permissions = locationPermissions) {
            mapViewModel.requestLocation()
            mapViewModel.setCameraPositionState(mapViewModel.presentLocation)
            called.invoke()
        }
    }
}

/**
 * 카메라 범위안에 있는 곳에 마커 찍기
 */
@Composable
private fun GetMarkerInCameraBound(cameraPositionState: CameraPositionState, mapViewModel: MapViewModel) {
    if(!cameraPositionState.isMoving) {
        val context = LocalContext.current
        val bitmapDrawable = AppCompatResources.getDrawable(context, R.drawable.bus_station)?.toBitmap()
        mapViewModel.stationMap.forEach {
            val position = LatLng(it.value.first, it.value.second)
            if(cameraPositionState.projection!!.visibleRegion.latLngBounds.contains(position)) {
                Marker(
                    state = rememberMarkerState(position = position),
                    icon = BitmapDescriptorFactory.fromBitmap(bitmapDrawable!!)
                )
            }
        }
    }
}

/**
 * Map Cluster 용 모든 위치 가져오기
 */
@Composable
private fun GetAllMarker(mapViewModel: MapViewModel, items : SnapshotStateList<ClusterData>) {
    LaunchedEffect(Unit) {
        mapViewModel.stationMap.forEach {
            val position = LatLng(it.value.first, it.value.second)
            items.add(ClusterData(position, "Marker", "Snippet"))
        }
    }
}

/**
 * Map Clustering
 */
@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun MapClustering(cameraPositionState: CameraPositionState, items : List<ClusterData>) {
    val context = LocalContext.current
    var clusterManager by remember { mutableStateOf<ClusterManager<ClusterData>?>(null) }
    MapEffect(items) { map ->
        if(clusterManager == null) {
            clusterManager = ClusterManager<ClusterData>(context, map)
            val customRenderer = MarkerClusterRenderer(context, map, clusterManager)
            clusterManager!!.renderer = customRenderer
        }
        clusterManager?.addItems(items)
    }
    LaunchedEffect(key1 = cameraPositionState.isMoving) {
        if(!cameraPositionState.isMoving) {
            clusterManager?.onCameraIdle()
        }
    }
}

@Composable
private fun rememberGoogleMapState(
    isMapLoaded : Boolean,
    reqLastLocation : Boolean,
    clusterData: ClusterData,
    cameraPositionState: CameraPositionState
) : GoogleMapStateHolder {
    return remember(isMapLoaded, reqLastLocation) {
        GoogleMapStateHolder(isMapLoaded, reqLastLocation, clusterData, cameraPositionState)
    }
}