package kr.co.inforexseoul.core_network.service

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.PapagoTranslateDataModel
import retrofit2.http.*

interface PapagoApiService {

    @FormUrlEncoded
    @POST("n2mt")
    fun translateText(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Field("source") source: String,
        @Field("target") target: String,
        @Field("text") text: String
    ): Flow<PapagoTranslateDataModel>
}