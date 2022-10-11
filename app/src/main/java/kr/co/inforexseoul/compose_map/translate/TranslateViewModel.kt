package kr.co.inforexseoul.compose_map.translate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kr.co.inforexseoul.compose_map.BuildConfig
import kr.co.inforexseoul.core_data.usecase.GetGoogleTranslateTextUseCase
import kr.co.inforexseoul.core_data.usecase.GetTranslateTextUseCase
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(
    private val getTranslateTextUseCase: GetTranslateTextUseCase,
    private val getGoogleTranslateTextUseCase : GetGoogleTranslateTextUseCase
) : ViewModel() {

    private val _translateState = MutableStateFlow<TranslateState>(TranslateState.UnInit)
    var translateState = _translateState

    private val googleLanguageMap = HashMap<String, String>().apply {
        put("ko", TranslateLanguage.KOREAN) // 한국어
        put("en", TranslateLanguage.ENGLISH) // 영어
        put("zh-CN", TranslateLanguage.CHINESE) // 중국어
        put("ja", TranslateLanguage.JAPANESE) // 일본어
        put("fr", TranslateLanguage.FRENCH) // 프랑스어
    }

    fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        apiText: String
    ) {
        when (apiText) {
            "Firebase ML Kit" -> {
                mlKitTranslate(text, sourceLanguage, targetLanguage)
                    .onEach { _translateState.value = it }
                    .launchIn(viewModelScope)
            }
            "Google Translation" -> {
                getGoogleTranslateTextUseCase.invoke(
                    key = BuildConfig.GOOGLE_TRANSLATION_KEY,
                    source = sourceLanguage,
                    target = targetLanguage,
                    text = text
                )
                .onStart { _translateState.value = TranslateState.Loading }
                .map { _translateState.value = TranslateState.Success(it) }
                .catch { _translateState.value = TranslateState.Error("error")}
                .launchIn(viewModelScope)
            }
            "Naver Papago" -> {
                getTranslateTextUseCase.invoke(
                    clientId = BuildConfig.NAVER_CLIENT_ID,
                    clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
                    source = sourceLanguage,
                    target = targetLanguage,
                    text = text
                )
                .onStart { _translateState.value = TranslateState.Loading }
                .map { _translateState.value = TranslateState.Success(it) }
                .catch { _translateState.value = TranslateState.Error("error")}
                .launchIn(viewModelScope)
            }
        }
    }

    private fun mlKitTranslate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): Flow<TranslateState> = callbackFlow {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(googleLanguageMap[sourceLanguage]!!)
            .setTargetLanguage(googleLanguageMap[targetLanguage]!!)
            .build()
        val translator = Translation.getClient(options)

        trySend(TranslateState.Loading)
        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { translatedText ->
                        trySend(TranslateState.Success(translatedText))
                    }
                    .addOnFailureListener {
                        trySend(TranslateState.Error("번역실패"))
                    }
            }
            .addOnFailureListener {
                trySend(TranslateState.Error("언어팩 다운로드 실패"))
            }
            .addOnCanceledListener {
                trySend(TranslateState.Error("언어팩 유저 캔슬"))
            }

        awaitClose { translator.close() }
    }
}

sealed interface TranslateState {
    object UnInit : TranslateState
    object Loading : TranslateState
    data class Error(val errorMsg: String) : TranslateState
    data class Success(val data: String) : TranslateState
}