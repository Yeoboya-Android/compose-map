package kr.co.inforexseoul.common_ui.component

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainSnackBar(
    message: String,
    actionLabel: String,
    duration: SnackbarDuration = SnackbarDuration.Long,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    cancel: () -> Unit = {},
    success: () -> Unit = {},
) {
    Scaffold(scaffoldState = scaffoldState) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = duration
            ).also { snackBarResult ->
                when (snackBarResult) {
                    SnackbarResult.Dismissed -> cancel.invoke()
                    SnackbarResult.ActionPerformed -> success.invoke()
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainSnackBar(
    @StringRes messageRes: Int,
    actionLabel: String,
    duration: SnackbarDuration = SnackbarDuration.Long,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    cancel: () -> Unit = {},
    success: () -> Unit = {},
) = MainSnackBar(stringResource(messageRes), actionLabel, duration, scaffoldState, cancel, success)