package kr.co.inforexseoul.compose_map.map

import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.co.inforexseoul.common_model.test_model.BusStationInfo
import kr.co.inforexseoul.common_ui.UIConstants
import kr.co.inforexseoul.common_ui.component.CustomToggleGroup
import kr.co.inforexseoul.common_ui.component.TextButton
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.locationPermissions
import kr.co.inforexseoul.compose_map.R
import kr.co.inforexseoul.compose_map.map.google.OpenGoogleMap
import kr.co.inforexseoul.compose_map.map.naver.OpenNaverMap
import kr.co.inforexseoul.compose_map.search.SearchDialog
import kr.co.inforexseoul.core_data.state.Result

@Composable
fun MapScreen(
    mapViewModel: MapViewModel = viewModel(),
    appbarTitle: MutableState<@Composable () -> Unit>
) {
    var mapState by remember { mutableStateOf<MapState>(MapState.GoogleMap) }
    val searchDialogOpen = remember { MutableTransitionState(false) }
    var searchWord by remember { mutableStateOf("") }


    GetBusStationInfo(mapViewModel) {
        CheckPermission(permissions = locationPermissions) {
            mapViewModel.requestLocation()
        }
        ScreenSwitch(mapState) {
            appbarTitle.value = { SearchButton(searchWord, searchDialogOpen) }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val list = listOf(stringResource(id = R.string.map_google), stringResource(id = R.string.map_naver))
                CustomToggleGroup(options =  list) { text ->
                    searchWord = ""
                    mapViewModel.setCameraPositionState(CameraPositionWrapper.UnInit)

                    when (text) {
                        list[0] -> MapState.GoogleMap
                        list[1] -> MapState.NaverMap
                        else -> null
                    }?.also { mapState = it }
                }
            }
        }
    }
    SearchDialog(searchDialogOpen = searchDialogOpen) { district ->
        searchWord = district.districtName
        mapViewModel.setCameraPositionState(mapState, district.latitude, district.longitude)
    }
}

@Composable
fun SearchButton(
    searchWord: String,
    searchDialogOpen: MutableTransitionState<Boolean>
) {
    TextButton(
        text = searchWord.ifEmpty { stringResource(R.string.search_btn) },
        fontSize = UIConstants.FONT_SIZE_LARGE.sp,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .defaultMinSize(1.dp)
            .fillMaxWidth()
            .offset(x = (-32).dp),
        onClick = { searchDialogOpen.targetState = true }
    )
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

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun GetBusStationInfo(mapViewModel: MapViewModel, success: @Composable () -> Unit) {
    val result by mapViewModel.busStationState.collectAsStateWithLifecycle()

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

