package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.OpenWeatherMapRepository
import kr.co.inforexseoul.core_data.state.resultFlow
import javax.inject.Inject


class GetOpenWeatherMapDataUseCase @Inject constructor(
    private val openWeatherMapRepository: OpenWeatherMapRepository
) {
    operator fun invoke(
        appId: String,
        latitude: Double,
        longitude: Double,
        exclude: String
    ) = openWeatherMapRepository.getOpenWeatherMapForecast(appId, latitude, longitude, exclude).resultFlow
}