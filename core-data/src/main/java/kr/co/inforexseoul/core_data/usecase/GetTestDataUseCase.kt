package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.TestRepository
import kr.co.inforexseoul.core_data.state.resultFlow
import javax.inject.Inject

/**
 * TODO 테스트용
 * */
class GetTestDataUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    operator fun invoke() = testRepository.getCharacterInfoList().resultFlow
}