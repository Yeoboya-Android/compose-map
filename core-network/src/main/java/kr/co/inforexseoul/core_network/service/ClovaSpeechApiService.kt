package kr.co.inforexseoul.core_network.service

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ClovaSpeechApiService {

    // 동영상 자막 서비스
    @POST("recognizer/url")
    fun getSubtitles(
        @Header("X-CLOVASPEECH-API-KEY") secretKey: String,
        @Body body: RequestBody
    ) : Flow<ClovaSpeechDataModel>

}