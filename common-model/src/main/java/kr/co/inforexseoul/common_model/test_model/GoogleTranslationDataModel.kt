package kr.co.inforexseoul.common_model.test_model

import com.google.gson.annotations.SerializedName

data class GoogleTranslationDataModel(
    @SerializedName("data") val data: GoogleTranslationData = GoogleTranslationData(),
)

data class GoogleTranslationData(
    @SerializedName("translations") val translations: List<GoogleTranslation> = listOf()
)

data class GoogleTranslation(
    @SerializedName("translatedText") val translatedText: String = ""
)