package kr.co.inforexseoul.core_network.service

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ClovaSpeechApiService {

    // 동영상 자막 서비스
    @POST("recognizer/url")
    fun getSubtitles(
        @Header("X-CLOVASPEECH-API-KEY") secretKey: String,
        @Body body: RequestBody
    ) : Flow<ClovaSpeechDataModel>

    // 동영상 자막 서비스
    @Multipart
    @POST("recognizer/upload")
    fun getSubtitlesWithUpload(
        @Header("X-CLOVASPEECH-API-KEY") secretKey: String,
        @Part media: MultipartBody.Part,
        @Part("params") params: RequestBody
    ) : Flow<ClovaSpeechDataModel>

}