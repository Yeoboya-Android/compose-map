package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel

interface ClovaSpeechRemoteDataSource {
    fun getSubtitles(
        secretKey: String,
        url: String,
        language: String
    ): Flow<ClovaSpeechDataModel>
}