package kr.co.inforexseoul.common_util.extension

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kr.co.inforexseoul.common_model.test_model.state.SpeechState

fun SpeechRecognizer.startRecognizer(intent: Intent): Flow<SpeechState> = callbackFlow {
    setRecognitionListener(object : RecognitionListener {
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
    startListening(intent)
    awaitClose { destroy() }
}