package kr.co.inforexseoul.core_data.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel
import kr.co.inforexseoul.core_network.datasource.ClovaSpeechRemoteDataSource
import java.io.File
import javax.inject.Inject

class ClovaSpeechRepositoryImpl @Inject constructor(
    private val clovaSpeechRemoteDataSource: ClovaSpeechRemoteDataSource
) : ClovaSpeechRepository {

    override fun getSubtitles(
        secretKey: String,
        url: String,
        language: String
    ): Flow<ClovaSpeechDataModel> {
        return clovaSpeechRemoteDataSource.getSubtitles(secretKey, url, language)
    }

    override fun getSubtitles(
        secretKey: String,
        file: File,
        language: String
    ): Flow<ClovaSpeechDataModel> {
        return clovaSpeechRemoteDataSource.getSubtitles(secretKey, file, language)
    }
}