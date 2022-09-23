package kr.co.inforexseoul.compose_map.map.google

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.maps.android.compose.CameraPositionState

class GoogleMapStateHolder(
    isMapLoaded : Boolean,
    reqLastLocation : Boolean,
    clusterData: ClusterData,
    inputCameraPositionState: CameraPositionState
) {
    var isMapLoaded by mutableStateOf(isMapLoaded)
    var reqLastLocation by mutableStateOf(reqLastLocation)
    val items = mutableStateListOf(clusterData)
    val cameraPositionState = inputCameraPositionState
}