package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.OpenWeatherForecastModel
import kr.co.inforexseoul.core_network.datasource.OpenWeatherRemoteDataSource
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val openWeatherRemoteDataSource: OpenWeatherRemoteDataSource
) : OpenWeatherRepository {
    override fun getOpenWeatherForecast(
        appId: String,
        latitude: Double,
        longitude: Double,
        exclude: String
    ): Flow<OpenWeatherForecastModel> {
        return openWeatherRemoteDataSource.getOpenWeatherForecast(appId, latitude, longitude, exclude)
    }
}