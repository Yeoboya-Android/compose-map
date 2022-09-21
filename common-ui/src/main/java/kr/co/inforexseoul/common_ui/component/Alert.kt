package kr.co.inforexseoul.common_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kr.co.inforexseoul.common_ui.UIConstants

@Composable
fun Alert(
    title: String? = null,
    message: String,
    open: MutableState<Boolean>,
    confirmText: String = UIConstants.ALERT_CONFIRM_BTN_TEXT,
    onConfirm: () -> Unit = {},
) {
    Dialog(onDismissRequest = { open.value = false }) {
        AlertContent(
            title = title,
            message = message,
            open = open,
            confirmText = confirmText,
            cancelText = null,
            onConfirm = onConfirm,
            onCancel = null,
        )
    }
}

@Composable
fun Confirm(
    title: String? = null,
    message: String,
    open: MutableState<Boolean>,
    confirmText: String = UIConstants.ALERT_CONFIRM_BTN_TEXT,
    cancelText: String = UIConstants.ALERT_CANCEL_BTN_TEXT,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    Dialog(onDismissRequest = { open.value = false }) {
        AlertContent(
            title = title,
            message = message,
            open = open,
            confirmText = confirmText,
            cancelText = cancelText,
            onConfirm = onConfirm,
            onCancel = onCancel,
        )
    }
}

@Composable
private fun AlertContent(
    title: String? = null,
    message: String,
    open: MutableState<Boolean>,
    confirmText: String,
    cancelText: String?,
    onConfirm: () -> Unit,
    onCancel: (() -> Unit)?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(UIConstants.ALERT_RADIUS.dp))
            .background(MaterialTheme.colors.background)
            .padding(top = if (title == null) UIConstants.SPACING_LARGE.dp else UIConstants.SPACING_MEDIUM.dp)
    ) {
        if (title != null){
            CommonText(
                modifier = Modifier.padding(
                    horizontal = UIConstants.SPACING_MEDIUM.dp,
                    vertical = UIConstants.SPACING_SMALL.dp
                ),
                text = title,
                fontSize = UIConstants.FONT_SIZE_LARGE.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        CommonText(
            modifier = Modifier.padding(
                horizontal = UIConstants.SPACING_MEDIUM.dp,
                vertical = UIConstants.SPACING_SMALL.dp
            ),
            text = message,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.height(UIConstants.ALERT_BUTTON_CONTAINER_HEIGHT.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (onCancel != null && cancelText != null) {
                TextButton(
                    text = cancelText,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ) {
                    open.value = false
                    onCancel()
                }
            }
            TextButton(
                text = confirmText,
                modifier = Modifier.weight(1f).fillMaxHeight(),
                contentColor = MaterialTheme.colors.primary
            ) {
                open.value = false
                onConfirm()
            }
        }
    }

}