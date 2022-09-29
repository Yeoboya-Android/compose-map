package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.DistrictRepository
import kr.co.inforexseoul.core_data.state.resultFlow
import javax.inject.Inject

class FindDistrictNamesUseCase @Inject constructor(
    private val districtRepository: DistrictRepository
) {
    operator fun invoke(word: String) = districtRepository.findDistrictNames("%$word%").resultFlow
}