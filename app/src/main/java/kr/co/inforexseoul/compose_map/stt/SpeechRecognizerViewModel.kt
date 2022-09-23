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

    private fun Context.startRecognizer(): Flow<SpeechState> = callbackFlow {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
        }
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this@startRecognizer)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onBeginningOfSpeech() = Unit
            override fun onRmsChanged(rmsdB: Float) = Unit
            override fun onBufferReceived(buffer: ByteArray?) = Unit
            override fun onPartialResults(partialResults: Bundle?) = Unit
            override fun onEvent(eventType: Int, params: Bundle?) = Unit

            override fun onReadyForSpeech(params: Bundle?) {
                trySend(SpeechState.Operation.ReadyForSpeech)
            }

            override fun onEndOfSpeech() {
                trySend(SpeechState.Operation.EndOfSpeech)
            }

            override fun onError(error: Int) {
                trySend(SpeechState.Completed.Fail(error))
                close()
            }

            override fun onResults(results: Bundle?) {
                trySend(SpeechState.Completed.Success(results))
                close()
            }
        })
        speechRecognizer.startListening(intent)
        awaitClose { speechRecognizer.setRecognitionListener(null) }
    }
}