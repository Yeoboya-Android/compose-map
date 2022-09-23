package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.OpenWeatherMapDataModel

interface OpenWeatherMapRemoteDataSource {
    fun getOpenWeatherMapForecast(
        appId: String,
        latitude: Double,
        longitude: Double,
        exclude: String
    ): Flow<OpenWeatherMapDataModel>
}