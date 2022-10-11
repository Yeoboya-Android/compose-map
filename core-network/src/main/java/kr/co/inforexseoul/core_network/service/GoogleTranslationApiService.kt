package kr.co.inforexseoul.core_network.service

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.GoogleTranslationDataModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GoogleTranslationApiService {

    @FormUrlEncoded
    @POST("language/translate/v2")
    fun translateText(
        @Field("key") key: String,
        @Field("source") source: String,
        @Field("target") target: String,
        @Field("q") text: String,
        @Field("format") format: String = "text",
        @Field("model") model: String = "nmt",
    ): Flow<GoogleTranslationDataModel>
}