package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.VillageForecastRepository
import kr.co.inforexseoul.core_data.state.resultFlow
import javax.inject.Inject


class GetVillageForecastUseCase @Inject constructor(
    private val villageForecastRepository: VillageForecastRepository
) {
    operator fun invoke(
        serviceKey: String,
        numOfRows: Int,
        pageNo: Int,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ) = villageForecastRepository.getVillageForecast(
        serviceKey,
        numOfRows,
        pageNo,
        baseDate,
        baseTime,
        nx,
        ny
    ).resultFlow
}