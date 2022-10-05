package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.ClovaSpeechRepository
import kr.co.inforexseoul.core_data.state.resultFlow
import javax.inject.Inject

class GetClovaSubtitlesUseCase @Inject constructor(
    private val clovaSpeechRepository: ClovaSpeechRepository
) {
    operator fun invoke(
        secretKey: String,
        url: String,
        language: String
    ) = clovaSpeechRepository.getSubtitles(
        secretKey,
        url,
        language
    ).resultFlow
}