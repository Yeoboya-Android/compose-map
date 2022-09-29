package kr.co.inforexseoul.core_data.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_data.repository.DistrictRepository
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_data.state.resultFlow
import kr.co.inforexseoul.core_database.entity.District
import javax.inject.Inject

class UpdateAddRecentSearchDistrictUseCase @Inject constructor(
    private val districtRepository: DistrictRepository
) {
    operator fun invoke(district: District): Flow<Result<Unit>> {
        val newDistrict = district.copy(recentSearchYn = "y")
        return districtRepository.updateAddRecentSearchDistricts(newDistrict).resultFlow
    }
}