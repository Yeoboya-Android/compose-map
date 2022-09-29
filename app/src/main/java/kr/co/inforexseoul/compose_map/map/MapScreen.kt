package kr.co.inforexseoul.compose_map.map

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
fun MapScreen(
    mapViewModel: MapViewModel = viewModel(),
    appbarTitle: MutableState<@Composable () -> Unit>
) {
    appbarTitle.value = {
        Text(
            text = "지도",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().offset(x = (-32).dp)
        )
    }

    var mapState by remember { mutableStateOf<MapState>(MapState.GoogleMap) }

    GetBusStationInfo(mapViewModel){
        CheckPermission(permissions = locationPermissions) {
            mapViewModel.requestLocation()
        }
        ScreenSwitch(mapViewModel, mapState) { selectedState ->
            mapState = selectedState
        }
    }
}

/**
 *  지도 화면 전환 (Google Map, Naver Map)
 */
@Composable
private fun ScreenSwitch(mapViewModel: MapViewModel, mapState: MapState, selected: (MapState) -> Unit) {

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

        CustomToggleGroup(
            options = mapViewModel.list,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) { text ->
                when(text) {
                    "Google Map" -> selected(MapState.GoogleMap)
                    "Naver Map" -> selected(MapState.NaverMap)
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

