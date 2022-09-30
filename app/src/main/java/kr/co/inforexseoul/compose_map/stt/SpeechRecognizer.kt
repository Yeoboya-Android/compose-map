package kr.co.inforexseoul.compose_map.stt

import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.co.inforexseoul.common_model.test_model.state.SpeechState
import kr.co.inforexseoul.common_ui.component.BottomSlideDialog
import kr.co.inforexseoul.common_ui.component.TextButton
import kr.co.inforexseoul.common_util.extension.stringList
import kr.co.inforexseoul.common_util.ui.collectAsStateWithLifecycle
import kr.co.inforexseoul.compose_map.R

@Composable
fun SpeechRecognizerDialog(
    speechRecognizerDialogOpen: MutableTransitionState<Boolean>,
    result: (String) -> Unit
) {
    if (speechRecognizerDialogOpen.targetState) {
        BottomSlideDialog(speechRecognizerDialogOpen) {
            SpeechRecognizer(speechRecognizerDialogOpen) { textList ->
                speechRecognizerDialogOpen.targetState = false
                result.invoke(textList.first())
            }
        }
    }
}

@Composable
fun SpeechRecognizer(
    speechRecognizerDialogOpen: MutableTransitionState<Boolean>,
    speechRecognizerViewModel: SpeechRecognizerViewModel = viewModel(),
    success: @Composable (List<String>) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val speechState by speechRecognizerViewModel.speechState.collectAsStateWithLifecycle(initial = SpeechState.UnInit)
    @StringRes var showSnackBar by remember { mutableStateOf<Int?>((null)) }

    LaunchedEffect(lifecycleOwner) {
        speechRecognizerViewModel.initRecognizer()
    }

    DisposableEffect(lifecycleOwner) {
        onDispose { speechRecognizerViewModel.dispose() }
    }

    TextButton(text = "스타트!") {
        if (speechState !is SpeechState.Operation)
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
            }.also { message ->
                showSnackBar = message
                speechRecognizerDialogOpen.targetState = false
            }
        }
        else -> Unit
    }

    if (showSnackBar != null) {
        Toast.makeText(context, stringResource(showSnackBar!!), Toast.LENGTH_SHORT).show()
    }
}