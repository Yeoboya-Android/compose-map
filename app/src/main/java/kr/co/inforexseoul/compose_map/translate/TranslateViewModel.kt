package kr.co.inforexseoul.compose_map.translate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kr.co.inforexseoul.compose_map.BuildConfig
import kr.co.inforexseoul.core_data.usecase.GetTranslateTextUseCase
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(
    private val getTranslateTextUseCase: GetTranslateTextUseCase,
) : ViewModel() {

    private val _translateState = MutableStateFlow<TranslateState>(TranslateState.UnInit)
    var translateState = _translateState

    private val googleLanguageMap = HashMap<String, Int>().apply {
        put("ko", FirebaseTranslateLanguage.KO) // 한국어
        put("en", FirebaseTranslateLanguage.EN) // 영어
        put("zh-CN", FirebaseTranslateLanguage.ZH) // 중국어
        put("ja", FirebaseTranslateLanguage.JA) // 일본어
        put("fr", FirebaseTranslateLanguage.FR) // 프랑스어
    }

    fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        isMlKit: Boolean
    ) {
        if (isMlKit) {
            mlKitTranslate(text, sourceLanguage, targetLanguage)
                .onEach { _translateState.value = it }
                .launchIn(viewModelScope)
        } else {
            getTranslateTextUseCase.invoke(
                clientId = BuildConfig.NAVER_CLIENT_ID,
                clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
                source = sourceLanguage,
                target = targetLanguage,
                text = text
            )
            .onStart { _translateState.value = TranslateState.Loading }
            .map { _translateState.value = TranslateState.Success(it) }
            .catch {
                Log.e("qwe123", "error: ${it.stackTraceToString()}")
                _translateState.value = TranslateState.Error("error")
            }
            .launchIn(viewModelScope)
        }
    }

    private fun mlKitTranslate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): Flow<TranslateState> = callbackFlow {
        val options = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(googleLanguageMap[sourceLanguage]!!)
            .setTargetLanguage(googleLanguageMap[targetLanguage]!!)
            .build()
        val translator = FirebaseNaturalLanguage.getInstance().getTranslator(options)

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