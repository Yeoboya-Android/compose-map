package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel
import kr.co.inforexseoul.core_network.service.ClovaSpeechApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject


class ClovaSpeechRemoteDataSourceImpl @Inject constructor(
    private val clovaSpeechApiService: ClovaSpeechApiService
) : ClovaSpeechRemoteDataSource {
    override fun getSubtitles(
        secretKey: String,
        url: String,
        language: String
    ): Flow<ClovaSpeechDataModel> {
        val body = JSONObject().run {
            put("url", url)
            put("language", language)
            put("completion", "sync")
            RequestBody.create("application/json".toMediaTypeOrNull(), this.toString())
        }

        return clovaSpeechApiService.getSubtitles(secretKey, body)
    }
}