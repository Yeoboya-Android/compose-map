package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.DistrictRepository
import kr.co.inforexseoul.core_data.state.resultFlow
import javax.inject.Inject

class GetRecentSearchDistrictsUseCase @Inject constructor(
    private val districtRepository: DistrictRepository
) {
    operator fun invoke() = districtRepository.getRecentSearchDistricts().resultFlow
}