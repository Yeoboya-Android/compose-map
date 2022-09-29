package kr.co.inforexseoul.core_data.usecase

import kr.co.inforexseoul.core_data.repository.PapagoRepository
import javax.inject.Inject

class GetTranslateTextUseCase @Inject constructor(
    private val papagoRepository: PapagoRepository
) {
    operator fun invoke(
        clientId: String,
        clientSecret: String,
        source: String,
        target: String,
        text: String
    ) = papagoRepository.translateText(
        clientId,
        clientSecret,
        source,
        target,
        text
    )
}