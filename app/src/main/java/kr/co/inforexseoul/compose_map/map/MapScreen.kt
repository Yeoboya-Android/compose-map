package kr.co.inforexseoul.compose_map.map

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.co.inforexseoul.common_model.test_model.BusStationInfo
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.locationPermissions
import kr.co.inforexseoul.common_util.ui.collectAsStateWithLifecycle
import kr.co.inforexseoul.core_data.state.Result

@Composable
fun MapScreen(mapViewModel: MapViewModel = viewModel()) {
    var mapState by remember { mutableStateOf<MapState>(MapState.GoogleMap) }

    CheckPermission(permissions = locationPermissions) {
        mapViewModel.requestLocation()
    }

    ScreenSwitch(mapState) { selectedState ->
        mapState = selectedState
    }

    GetBusStationInfo(mapViewModel)
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

// TODO Common 으로 옮기는 작업 필요
@Composable
private fun CustomToggleGroup(
    options : List<String>,
    style : TextStyle = MaterialTheme.typography.body1.merge(),
    cornerShape : Shape = RoundedCornerShape(12.dp),
    paddingVertical : Dp = 12.dp,
    paddingHorizontal : Dp = 16.dp,
    @ColorRes contentColor : Color = Color.White,
    @ColorRes selectedColor : Color = MaterialTheme.colors.primary,
    @ColorRes unSelectedColor : Color = Color.LightGray,
    modifier: Modifier = Modifier,
    clickable : (String) -> Unit
) {
    var selectedOption by remember { mutableStateOf(options[0]) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        options.forEach { text ->
            Row {
                Text(
                    text = text,
                    style = style,
                    color = contentColor,
                    modifier = Modifier
                        .clip(shape = cornerShape)
                        .clickable {
                            selectedOption = text
                            clickable.invoke(text)
                        }
                        .background(
                            if (text == selectedOption) {
                                selectedColor
                            } else {
                                unSelectedColor
                            }
                        )
                        .padding(
                            vertical = paddingVertical,
                            horizontal = paddingHorizontal
                        )
                )
            }
        }
    }
}

@Composable
fun GetBusStationInfo(mapViewModel : MapViewModel) {
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
        }
    }
}

sealed interface MapState {
    object GoogleMap : MapState
    object NaverMap : MapState
}

