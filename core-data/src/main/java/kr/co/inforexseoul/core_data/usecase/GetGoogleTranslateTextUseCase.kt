package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.GoogleTranslationRepository
import javax.inject.Inject

class GetGoogleTranslateTextUseCase @Inject constructor(
    private val googleTranslationRepository: GoogleTranslationRepository
) {
    operator fun invoke(
        key: String,
        source: String,
        target: String,
        text: String
    ) = googleTranslationRepository.translateText(
        key,
        source,
        target,
        text
    )
}