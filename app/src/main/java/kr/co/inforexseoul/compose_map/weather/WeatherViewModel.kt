package kr.co.inforexseoul.compose_map.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kr.co.inforexseoul.compose_map.BuildConfig
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_data.usecase.GetOpenWeatherMapDataUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    getOpenWeatherMapDataUseCase: GetOpenWeatherMapDataUseCase
) : ViewModel() {

    val openWeatherMapState =
        getOpenWeatherMapDataUseCase.invoke(
            appId = BuildConfig.OPEN_WEATHER_MAP_KEY,
            latitude = 35.1470,
            longitude = 126.8452,
            exclude = "daily,minutely,current"
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Result.Loading
        )
}