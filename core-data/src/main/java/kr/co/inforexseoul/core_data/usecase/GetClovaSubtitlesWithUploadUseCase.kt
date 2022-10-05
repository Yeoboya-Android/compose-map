package kr.co.inforexseoul.core_data.usecase

import android.net.Uri
import kr.co.inforexseoul.core_data.repository.ClovaSpeechRepository
import kr.co.inforexseoul.core_data.state.resultFlow
import java.io.File
import javax.inject.Inject

class GetClovaSubtitlesWithUploadUseCase @Inject constructor(
    private val clovaSpeechRepository: ClovaSpeechRepository
) {
    operator fun invoke(
        secretKey: String,
        file: File,
        language: String
    ) = clovaSpeechRepository.getSubtitles(
        secretKey,
        file,
        language
    ).resultFlow
}