package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel

interface ClovaSpeechRepository {
    fun getSubtitles(
        secretKey: String,
        url: String,
        language: String
    ): Flow<ClovaSpeechDataModel>
}