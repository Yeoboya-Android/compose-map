package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.OpenWeatherRepository
import kr.co.inforexseoul.core_data.state.resultFlow
import javax.inject.Inject


class GetOpenWeatherForecastUseCase @Inject constructor(
    private val openWeatherRepository: OpenWeatherRepository
) {
    operator fun invoke(
        appId: String,
        latitude: Double,
        longitude: Double,
        exclude: String
    ) = openWeatherRepository.getOpenWeatherForecast(appId, latitude, longitude, exclude).resultFlow
}