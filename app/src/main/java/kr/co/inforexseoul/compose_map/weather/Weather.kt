package kr.co.inforexseoul.compose_map.weather

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import kr.co.inforexseoul.common_model.test_model.*
import kr.co.inforexseoul.common_ui.UIConstants
import kr.co.inforexseoul.common_ui.component.BottomSlideDialog
import kr.co.inforexseoul.common_ui.component.CommonText
import kr.co.inforexseoul.common_ui.component.LoadingBar
import kr.co.inforexseoul.common_ui.theme.Mint20
import kr.co.inforexseoul.common_util.ui.collectAsStateWithLifecycle
import kr.co.inforexseoul.compose_map.R
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_database.entity.District

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherView(
    isGoogleMap: Boolean,
    coordinate: MutableState<Pair<Double, Double>?>,
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val open = remember { MutableTransitionState(false) }
    if (coordinate.value != null) {
        val result by weatherViewModel.districtState.collectAsStateWithLifecycle(Result.Loading)
        when (result) {
            is Result.Error -> Log.e("qwe123", "district error")
            is Result.Loading -> {
                Log.d("qwe123", "select district loading")
            }
            is Result.Success -> {
                val district = (result as Result.Success<District>).data
                Log.d("qwe123", "WeatherView()::: district name: ${district.districtName}")
                WeatherBottomSlideDialog(
                    open = open,
                    coordinate = coordinate,
                    isGoogleMap = isGoogleMap,
                    district = district,
                    weatherViewModel = weatherViewModel
                )
            }
        }
        weatherViewModel.getDistrict(coordinate.value!!.first, coordinate.value!!.second)
    }
}

@Composable
fun WeatherBottomSlideDialog(
    open: MutableTransitionState<Boolean>,
    coordinate: MutableState<Pair<Double, Double>?>,
    isGoogleMap: Boolean,
    district: District,
    weatherViewModel: WeatherViewModel
) {
    Log.d("qwe123", "WeatherBottomSlideDialog()::: district name: ${district.districtName}")
    if (isGoogleMap) {
        val result by weatherViewModel.openWeatherForecastState.collectAsStateWithLifecycle(Result.Loading)
        when (result) {
            is Result.Error -> Log.e("qwe123", "weather error")
            is Result.Loading -> LoadingBar()
            is Result.Success -> {
                BottomSlideDialog(
                    open = open,
                    onDismissRequest = { coordinate.value = null }
                ) {
                    OpenWeatherForecastContent(
                        title = district.districtName,
                        data = (result as Result.Success<OpenWeatherForecastModel>).data
                    )
                }
                open.targetState = true
            }
        }
        weatherViewModel.getOpenWeatherForecast(coordinate.value?.first ?: .0, coordinate.value?.second ?: .0)
    } else {
        val result by weatherViewModel.villageForecastState.collectAsStateWithLifecycle(Result.Loading)
        when (result) {
            is Result.Error -> Log.e("qwe123", "weather error")
            is Result.Loading -> LoadingBar()
            is Result.Success -> {
                val data = (result as Result.Success<VillageForecastItems>).data
                BottomSlideDialog(
                    open = open,
                    onDismissRequest = { coordinate.value = null }
                ) {
                    VillageForecastContent(
                        title = district.districtName,
                        data = data.getWeatherDataList()
                    )
                }
                open.targetState = true
            }
        }
        weatherViewModel.getVillageForecast(district.nx, district.ny)
    }
}

/** 기상청 API */
@Composable
fun VillageForecastContent(title: String, data: List<WeatherDataModel>) {
    Log.d("qwe123", "VillageForecastContent():::")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
            .padding(vertical = UIConstants.SPACING_MEDIUM.dp)
    ) {
        CommonText(
            text = title,
            fontSize = UIConstants.FONT_SIZE_LARGE.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            items(data) {
                VillageForecastItem(it)
            }
        }
    }
}

@Composable
fun VillageForecastItem(data: WeatherDataModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .background(color = MaterialTheme.colors.background)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        val iconResId: Int = when (data.condition) {
            is WeatherCondition.Clear -> R.drawable.ico_clear
            is WeatherCondition.Rain -> R.drawable.ico_rain
            is WeatherCondition.Clouds -> R.drawable.ico_clouds
            is WeatherCondition.Snow -> R.drawable.ico_snow
        }

        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier.size(60.dp)
        )

        CommonText(text = data.date)
        CommonText(text = data.time)
        CommonText(text = String.format("%d˚C", data.temperature))
    }
}

/** Open Weather API */
@Composable
fun OpenWeatherForecastContent(title: String, data: OpenWeatherForecastModel) {
    Log.d("qwe123", "OpenWeatherForecastContent():::")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
            .padding(vertical = UIConstants.SPACING_MEDIUM.dp)
    ) {
        CommonText(
            text = title,
            fontSize = UIConstants.FONT_SIZE_LARGE.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            items(data.hourly) {
                OpenWeatherForecastItem(it)
            }
        }
    }
}

@Composable
fun OpenWeatherForecastItem(data: Hourly) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .background(color = MaterialTheme.colors.background)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        data.weather.takeIf { it.isNotEmpty() }?.let {
            AsyncImage(
                model = data.getIconUrl(),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = Mint20,
                        shape = CircleShape
                    )
            )
        }
        CommonText(text = data.getDay())
        CommonText(text = data.getTime())
        CommonText(text = String.format("%.1f˚C", data.getCelsius()))
    }
}