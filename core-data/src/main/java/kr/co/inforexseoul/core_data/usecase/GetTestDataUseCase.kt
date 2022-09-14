package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.TestRepository
import javax.inject.Inject

class GetTestDataUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    operator fun invoke() = testRepository.getCharacterInfoList()
}