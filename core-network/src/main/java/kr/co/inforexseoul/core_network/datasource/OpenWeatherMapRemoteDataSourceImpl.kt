package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.OpenWeatherMapDataModel
import kr.co.inforexseoul.core_network.service.OpenWeatherMapApiService
import javax.inject.Inject


class OpenWeatherMapRemoteDataSourceImpl @Inject constructor(
    private val openWeatherMapApiService: OpenWeatherMapApiService
) : OpenWeatherMapRemoteDataSource {
    override fun getOpenWeatherMapForecast(
        appId: String,
        latitude: Double,
        longitude: Double,
        exclude: String
    ): Flow<OpenWeatherMapDataModel> {
        return openWeatherMapApiService.getOpenWeatherMapForecast(appId, latitude, longitude, exclude)
    }
}