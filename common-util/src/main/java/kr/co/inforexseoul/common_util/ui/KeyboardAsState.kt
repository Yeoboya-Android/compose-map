package kr.co.inforexseoul.common_util.ui

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView

@Composable
fun rememberKeyboardVisibleAsState(): State<Boolean> {
    val keyboardVisibleState = remember { mutableStateOf(false) }
    val view = LocalView.current

    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)

            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardVisibleState.value = keypadHeight > screenHeight * 0.15
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardVisibleState
}