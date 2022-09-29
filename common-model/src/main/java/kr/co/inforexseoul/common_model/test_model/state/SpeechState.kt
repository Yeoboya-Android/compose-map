package kr.co.inforexseoul.common_model.test_model.state

import android.os.Bundle

sealed interface SpeechState {
    object UnInit : SpeechState

    sealed interface Operation : SpeechState {
        object ReadyForSpeech : Operation
        object EndOfSpeech : Operation
    }

    sealed interface Completed : SpeechState {
        data class Fail(val error: Int) : Completed
        data class Success(val data: Bundle?) : Completed
    }
}