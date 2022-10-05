package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel
import kr.co.inforexseoul.core_network.service.ClovaSpeechApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
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

    override fun getSubtitles(
        secretKey: String,
        file: File,
        language: String
    ): Flow<ClovaSpeechDataModel> {
        val media = MultipartBody.Part.createFormData(
            "media",
            file.name,
            file.asRequestBody("multipart/form-data".toMediaType())
        )
        val params = JSONObject().run {
            put("language", language)
            put("completion", "sync")
            RequestBody.create("multipart/form-data".toMediaType(), this.toString())
        }
        return clovaSpeechApiService.getSubtitlesWithUpload(secretKey, media, params)
    }

}