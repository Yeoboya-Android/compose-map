package kr.co.inforexseoul.common_model.test_model

import com.google.gson.annotations.SerializedName

data class PapagoTranslateDataModel(
    @SerializedName("message") val message: PapagoTranslateMessage = PapagoTranslateMessage(),
)

data class PapagoTranslateMessage(
    @SerializedName("result") val result: PapagoTranslateResult = PapagoTranslateResult(),
)

data class PapagoTranslateResult(
    @SerializedName("translatedText") val translatedText: String = "",
)