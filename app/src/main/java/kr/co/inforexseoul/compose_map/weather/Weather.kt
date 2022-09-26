package kr.co.inforexseoul.compose_map.weather

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
import androidx.compose.runtime.getValue
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
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_database.entity.District
import kr.co.inforexseoul.compose_map.R

@Composable
fun WeatherView(
    open: MutableTransitionState<Boolean>,
    weatherViewModel: WeatherViewModel = viewModel()
) {
    if (open.targetState) {
        val result by weatherViewModel.districtState.collectAsStateWithLifecycle(Result.Loading)
        when (result) {
            is Result.Error -> Log.e("qwe123", "district error")
            is Result.Loading -> {
                Log.i("qwe123", ".WeatherView()::: Loading")
                LoadingBar()
            }
            is Result.Success -> {
                Log.d("qwe123", "WeatherView()::: Success")
                WeatherBottomSlideDialog(
                    open = open,
                    district = (result as Result.Success<District>).data,
                    weatherViewModel = weatherViewModel
                )
            }
        }
    }
}

@Composable
fun WeatherBottomSlideDialog(
    open: MutableTransitionState<Boolean>,
    district: District,
    weatherViewModel: WeatherViewModel
) {
    val isGoogle = true
    if (isGoogle) {
        val result by weatherViewModel.openWeatherForecastState.collectAsStateWithLifecycle(Result.Loading)
        when (result) {
            is Result.Error -> Log.e("qwe123", "error")
            is Result.Loading -> LoadingBar()
            is Result.Success -> {
                Log.d("qwe123", "WeatherBottomSlideDialog()::: Success")
                BottomSlideDialog(open = open) {
                    OpenWeatherForecastContent(
                        title = district.districtName,
                        data = (result as Result.Success<OpenWeatherForecastModel>).data
                    )
                }
            }
        }
    } else {
        val result by weatherViewModel.villageForecastState.collectAsStateWithLifecycle(Result.Loading)
        when (result) {
            is Result.Error -> Log.e("qwe123", "error")
            is Result.Loading -> LoadingBar()
            is Result.Success -> {
                val data = (result as Result.Success<VillageForecastItems>).data
                BottomSlideDialog(open = open) {
                    VillageForecastContent(
                        title = district.districtName,
                        data = data.getWeatherDataList()
                    )
                }

            }
        }
    }
}

/** 기상청 API */
@Composable
fun VillageForecastContent(title: String, data: List<WeatherDataModel>) {
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