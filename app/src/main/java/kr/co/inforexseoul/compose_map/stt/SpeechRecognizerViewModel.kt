package kr.co.inforexseoul.compose_map.stt

import android.app.Application
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.co.inforexseoul.common_model.test_model.state.SpeechState
import kr.co.inforexseoul.common_util.extension.startRecognizer
import javax.inject.Inject

class SpeechRecognizerViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val context: Context get() = app.applicationContext

    private val _speechState = MutableStateFlow<SpeechState>(SpeechState.UnInit)
    val speechState = _speechState.asStateFlow()

    var speechRecognizer: SpeechRecognizer? = null

    fun initRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    }

    fun startRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10_000) // 최소 녹음시간
        }
        speechRecognizer!!.startRecognizer(intent)
            .onEach { _speechState.value = it }
            .launchIn(viewModelScope)
    }

    fun dispose() {
        speechRecognizer?.apply {
            stopListening()
            cancel()
            destroy()
        }
        speechRecognizer = null
        _speechState.value = SpeechState.UnInit
    }
}

