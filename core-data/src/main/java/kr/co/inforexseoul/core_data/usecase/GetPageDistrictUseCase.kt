package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.DistrictRepository
import javax.inject.Inject

class GetPageDistrictUseCase @Inject constructor(
    private val districtRepository: DistrictRepository
) {
    operator fun invoke(keyword: String, index: Int, loadSize: Int) =
        districtRepository.getDistrictPageNames("%$keyword%", index, loadSize)
}