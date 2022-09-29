package kr.co.inforexseoul.common_util.extension

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onCompletion
import kr.co.inforexseoul.common_model.test_model.state.SpeechState


fun Context.startRecognizer(): Flow<SpeechState> = callbackFlow {
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
    awaitClose {
        speechRecognizer.setRecognitionListener(null)
    }
}.onCompletion { error ->
    if (error is CancellationException) {
        Log.d("123123", "캔슬됨!")
    }
}