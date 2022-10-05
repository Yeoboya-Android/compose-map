package kr.co.inforexseoul.common_model.test_model

import com.google.gson.annotations.SerializedName

data class ClovaSpeechDataModel(
    @SerializedName("result") val result: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("token") val token: String = "",
    @SerializedName("version") val version: String = "",
    @SerializedName("segments") val segments: List<ClovaSpeechSegment> = listOf(),
)

data class ClovaSpeechSegment(
    @SerializedName("start") val start: Long = 0,
    @SerializedName("end") val end: Long = 0,
    @SerializedName("text") val text: String = "",
    @SerializedName("confidence") val confidence: Double = .0
)
