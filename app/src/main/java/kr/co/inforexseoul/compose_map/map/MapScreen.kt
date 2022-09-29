package kr.co.inforexseoul.compose_map.map

import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.co.inforexseoul.common_model.test_model.BusStationInfo
import kr.co.inforexseoul.common_ui.component.CustomToggleGroup
import kr.co.inforexseoul.common_ui.component.TextButton
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.locationPermissions
import kr.co.inforexseoul.common_util.ui.collectAsStateWithLifecycle
import kr.co.inforexseoul.compose_map.map.google.OpenGoogleMap
import kr.co.inforexseoul.compose_map.map.naver.OpenNaverMap
import kr.co.inforexseoul.compose_map.search.SearchDialog
import kr.co.inforexseoul.core_data.state.Result

@Composable
fun MapScreen(mapViewModel: MapViewModel = viewModel()) {
    var mapState by remember { mutableStateOf<MapState>(MapState.GoogleMap) }
    val searchDialogOpen = remember { MutableTransitionState(false) }
    var searchWord by remember { mutableStateOf("") }

    GetBusStationInfo(mapViewModel) {
        CheckPermission(permissions = locationPermissions) {
            mapViewModel.requestLocation()
        }
        ScreenSwitch(mapState) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextButton(
                    text = searchWord.ifEmpty { "검색" },
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    onClick = { searchDialogOpen.targetState = true }
                )
                CustomToggleGroup(mapViewModel.list) { text ->
                    when (text) {
                        "Google Map" -> MapState.GoogleMap
                        "Naver Map" -> MapState.NaverMap
                        else -> null
                    }?.also { mapState = it }
                }
            }
        }
    }
    SearchDialog(searchDialogOpen = searchDialogOpen) { district ->
        searchWord = district.districtName
        mapViewModel.setCameraPositionState(district.latitude, district.longitude)
    }
}

/**
 *  지도 화면 전환 (Google Map, Naver Map)
 */
@Composable
private fun ScreenSwitch(
    mapState: MapState,
    content: @Composable () -> Unit
) {
    when (mapState) {
        is MapState.GoogleMap -> OpenGoogleMap()
        is MapState.NaverMap -> OpenNaverMap()
    }
    content.invoke()
}

@Composable
fun GetBusStationInfo(mapViewModel: MapViewModel, success: @Composable () -> Unit) {
    val result by mapViewModel.busStationState.collectAsStateWithLifecycle(initial = Result.Loading)

    when (result) {
        is Result.Error -> {
            Log.d("123123", "api call Error ${(result as Result.Error).error}")
        }
        is Result.Loading -> Unit
        is Result.Success -> {
            Log.d("123123", "데이터 가져오기 성공 : ${(result as Result.Success<BusStationInfo>).data}")
            mapViewModel.stationList = (result as Result.Success<BusStationInfo>).data.stationList
            mapViewModel.stationListToMap()
            success.invoke()
        }
    }
}

sealed interface MapState {
    object GoogleMap : MapState
    object NaverMap : MapState
}

