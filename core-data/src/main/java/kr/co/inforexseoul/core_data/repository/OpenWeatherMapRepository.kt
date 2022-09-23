package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.OpenWeatherMapDataModel

interface OpenWeatherMapRepository {
    fun getOpenWeatherMapForecast(
        appId: String,
        latitude: Double,
        longitude: Double,
        exclude: String
    ): Flow<OpenWeatherMapDataModel>
}