package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.MapRepository
import kr.co.inforexseoul.core_data.state.resultFlow
import javax.inject.Inject

class GetBusStationDataUseCase @Inject constructor(
    private val mapRepository: MapRepository
) {
    operator fun invoke(serviceKey : String) = mapRepository.getBusStationInfoList(serviceKey).resultFlow
}