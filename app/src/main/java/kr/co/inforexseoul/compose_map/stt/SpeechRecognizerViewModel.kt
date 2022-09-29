package kr.co.inforexseoul.compose_map.stt

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kr.co.inforexseoul.common_model.test_model.state.SpeechState
import kr.co.inforexseoul.common_util.extension.startRecognizer
import javax.inject.Inject

class SpeechRecognizerViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val context: Context get() = app.applicationContext

    private val _speechState = MutableStateFlow<SpeechState>(SpeechState.UnInit)
    val speechState = _speechState.asStateFlow()

    fun startRecognizer() {
        context
            .startRecognizer()
            .onEach { _speechState.value = it }
            .launchIn(viewModelScope)
    }
}