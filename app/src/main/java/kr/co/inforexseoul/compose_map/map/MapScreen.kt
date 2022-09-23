package kr.co.inforexseoul.compose_map.map

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.co.inforexseoul.common_model.test_model.BusStationInfo
import kr.co.inforexseoul.common_ui.component.CustomToggleGroup
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.locationPermissions
import kr.co.inforexseoul.common_util.ui.collectAsStateWithLifecycle
import kr.co.inforexseoul.compose_map.map.google.OpenGoogleMap
import kr.co.inforexseoul.compose_map.map.naver.OpenNaverMap
import kr.co.inforexseoul.core_data.state.Result

@Composable
fun MapScreen(mapViewModel: MapViewModel = viewModel()) {
    var mapState by remember { mutableStateOf<MapState>(MapState.GoogleMap) }

    GetBusStationInfo(mapViewModel){

        CheckPermission(permissions = locationPermissions) {
            mapViewModel.requestLocation()
        }

        ScreenSwitch(mapState) { selectedState ->
            mapState = selectedState
        }

    }
}

/**
 *  지도 화면 전환 (Google Map, Naver Map)
 */
@Composable
private fun ScreenSwitch(mapState: MapState, selected: (MapState) -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (mapState) {
            is MapState.GoogleMap -> {
                OpenGoogleMap()
            }
            is MapState.NaverMap -> {
                OpenNaverMap()
            }
        }

        val list = listOf("Google Map", "Naver Map")
        CustomToggleGroup(
            options = list,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) { text ->
                if (text == list[0]) {
                    selected(MapState.GoogleMap)
                } else {
                    selected(MapState.NaverMap)
                }
            }
    }
}

@Composable
fun GetBusStationInfo(mapViewModel : MapViewModel, success : @Composable () -> Unit) {
    val result by mapViewModel.busStationState.collectAsStateWithLifecycle(initial = Result.Loading)

    when(result) {
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

