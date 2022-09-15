package kr.co.inforexseoul.common_util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun <T> Flow<T>.collectAsStateWithLifecycle(
    initial: T,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<T> =
    flowWithLifecycle(LocalLifecycleOwner.current.lifecycle, minActiveState).collectAsState(initial)