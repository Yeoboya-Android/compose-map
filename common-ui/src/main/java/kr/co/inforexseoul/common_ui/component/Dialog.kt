package kr.co.inforexseoul.common_ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomSlideDialog(
    open: MutableTransitionState<Boolean>,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit
) {
    if (open.targetState) {
        Dialog(
            onDismissRequest = {
                open.targetState = false
                onDismissRequest.invoke()
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = { open.targetState = false },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
            ) {
                AnimatedVisibility(
                    visibleState = open,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .clickable(
                            onClick = { },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    enter = slideInVertically { it },
                    exit = slideOutVertically { it }
                ) {
                    content()
                }
            }
        }
    }
}