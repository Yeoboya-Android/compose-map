package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.OpenWeatherForecastModel
import kr.co.inforexseoul.core_network.service.OpenWeatherApiService
import javax.inject.Inject


class OpenWeatherRemoteDataSourceImpl @Inject constructor(
    private val openWeatherApiService: OpenWeatherApiService
) : OpenWeatherRemoteDataSource {
    override fun getOpenWeatherForecast(
        appId: String,
        latitude: Double,
        longitude: Double,
        exclude: String
    ): Flow<OpenWeatherForecastModel> {
        return openWeatherApiService.getOpenWeatherForecast(appId, latitude, longitude, exclude)
    }
}