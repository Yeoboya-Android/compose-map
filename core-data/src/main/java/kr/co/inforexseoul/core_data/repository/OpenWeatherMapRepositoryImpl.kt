package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.OpenWeatherMapDataModel
import kr.co.inforexseoul.core_network.datasource.OpenWeatherMapRemoteDataSource
import javax.inject.Inject

class OpenWeatherMapRepositoryImpl @Inject constructor(
    private val openWeatherMapRemoteDataSource: OpenWeatherMapRemoteDataSource
) : OpenWeatherMapRepository {
    override fun getOpenWeatherMapForecast(
        appId: String,
        latitude: Double,
        longitude: Double,
        exclude: String
    ): Flow<OpenWeatherMapDataModel> {
        return openWeatherMapRemoteDataSource.getOpenWeatherMapForecast(appId, latitude, longitude, exclude)
    }
}