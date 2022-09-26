package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.DistrictRepository
import kr.co.inforexseoul.core_data.state.resultFlow
import kr.co.inforexseoul.core_database.entity.District
import javax.inject.Inject

class SelectDistrictCountUseCase @Inject constructor(
    private val districtRepository: DistrictRepository
) {
    operator fun invoke() = districtRepository.getDistrictCount()
}