package kr.co.inforexseoul.core_network.datasource

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel
import java.io.File

interface ClovaSpeechRemoteDataSource {
    fun getSubtitles(
        secretKey: String,
        url: String,
        language: String
    ): Flow<ClovaSpeechDataModel>

    fun getSubtitles(
        secretKey: String,
        file: File,
        language: String
    ): Flow<ClovaSpeechDataModel>
}