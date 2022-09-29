package kr.co.inforexseoul.compose_map.stt

import android.speech.SpeechRecognizer
import androidx.annotation.StringRes
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.co.inforexseoul.common_model.test_model.state.SpeechState
import kr.co.inforexseoul.common_ui.component.BottomSlideDialog
import kr.co.inforexseoul.common_ui.component.MainSnackBar
import kr.co.inforexseoul.common_ui.component.TextButton
import kr.co.inforexseoul.common_util.extension.stringList
import kr.co.inforexseoul.common_util.ui.collectAsStateWithLifecycle
import kr.co.inforexseoul.compose_map.R

@Composable
fun SpeechRecognizerDialog(
    speechRecognizerDialogOpen: MutableTransitionState<Boolean> = MutableTransitionState(false),
    result: (String) -> Unit
) {
    BottomSlideDialog(speechRecognizerDialogOpen) {
        SpeechRecognizer { textList ->
            speechRecognizerDialogOpen.targetState = false
            result.invoke(textList.first())
        }
    }
}

@Composable
fun SpeechRecognizer(
    speechRecognizerViewModel: SpeechRecognizerViewModel = viewModel(),
    success: @Composable (List<String>) -> Unit
) {
    val speechState by speechRecognizerViewModel.speechState.collectAsStateWithLifecycle(initial = SpeechState.UnInit)
    val scaffoldState = rememberScaffoldState()
    @StringRes var showSnackBar by remember { mutableStateOf<Int?>((null)) }

    TextButton(text = "스타트!") {
        if (speechRecognizerViewModel.speechState.value !is SpeechState.Operation)
            speechRecognizerViewModel.startRecognizer()
        else
            showSnackBar = R.string.speech_error_message_8
    }

    when (speechState) {
        is SpeechState.Completed.Success -> {
            val textList = (speechState as SpeechState.Completed.Success)
                .data.stringList(SpeechRecognizer.RESULTS_RECOGNITION)

            if (textList.isNotEmpty())
                success.invoke(textList)
        }
        is SpeechState.Completed.Fail -> {
            when ((speechState as SpeechState.Completed.Fail).error) {
                SpeechRecognizer.ERROR_AUDIO -> R.string.speech_error_message_1
                SpeechRecognizer.ERROR_CLIENT -> R.string.speech_error_message_2
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> R.string.speech_error_message_3
                SpeechRecognizer.ERROR_NETWORK -> R.string.speech_error_message_4
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> R.string.speech_error_message_5
                SpeechRecognizer.ERROR_NO_MATCH -> R.string.speech_error_message_6
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> R.string.speech_error_message_7
                SpeechRecognizer.ERROR_SERVER -> R.string.speech_error_message_8
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> R.string.speech_error_message_9
                else -> R.string.speech_error_message_else
            }.also { showSnackBar = it }
        }
        else -> Unit
    }

    if (showSnackBar != null) {
        MainSnackBar(
            messageRes = showSnackBar!!,
            actionLabel = stringResource(R.string.snack_bar_close),
            scaffoldState = scaffoldState,
            cancel = { showSnackBar = null },
            success = { showSnackBar = null }
        )
    }
}