package kr.co.inforexseoul.common_ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.co.inforexseoul.common_ui.UIConstants
import kr.co.inforexseoul.common_ui.theme.Grey90


@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    disabledBackgroundColor: Color = Grey90,
    disabledContentColor: Color = Color.White,
    fontSize: TextUnit = UIConstants.FONT_SIZE_MEDIUM.sp,
    shape: Shape = RoundedCornerShape(UIConstants.BUTTON_RADIUS.dp),
    horizontalPadding: Dp = UIConstants.BUTTON_HORIZONTAL_PADDING.dp,
    verticalPadding: Dp = UIConstants.BUTTON_VERTICAL_PADDING.dp,
    isRipple: Boolean = true,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    CommonButton(
        modifier = modifier,
        text = text,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
        borderColor = null,
        fontSize = fontSize,
        shape = shape,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
        isRipple = isRipple,
        isEnabled = isEnabled,
        onClick = onClick
    )
}

@Composable
fun StrokeButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.primary,
    disabledBackgroundColor: Color = Grey90,
    disabledContentColor: Color = Color.White,
    borderColor: Color = MaterialTheme.colors.primary,
    fontSize: TextUnit = UIConstants.FONT_SIZE_MEDIUM.sp,
    shape: Shape = RoundedCornerShape(UIConstants.BUTTON_RADIUS.dp),
    horizontalPadding: Dp = UIConstants.BUTTON_HORIZONTAL_PADDING.dp,
    verticalPadding: Dp = UIConstants.BUTTON_VERTICAL_PADDING.dp,
    isRipple: Boolean = true,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    CommonButton(
        modifier = modifier,
        text = text,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
        borderColor = borderColor,
        fontSize = fontSize,
        shape = shape,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
        isRipple = isRipple,
        isEnabled = isEnabled,
        onClick = onClick
    )
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colors.onBackground,
    disabledBackgroundColor: Color = Grey90,
    disabledContentColor: Color = Color.White,
    fontSize: TextUnit = UIConstants.FONT_SIZE_MEDIUM.sp,
    shape: Shape = RectangleShape,
    horizontalPadding: Dp = UIConstants.BUTTON_HORIZONTAL_PADDING.dp,
    verticalPadding: Dp = UIConstants.BUTTON_VERTICAL_PADDING.dp,
    isRipple: Boolean = false,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    CommonButton(
        modifier = modifier,
        text = text,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
        borderColor = null,
        fontSize = fontSize,
        shape = shape,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
        isRipple = isRipple,
        isEnabled = isEnabled,
        onClick = onClick
    )
}

@Composable
private fun CommonButton(
    modifier: Modifier,
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    disabledBackgroundColor: Color,
    disabledContentColor: Color,
    borderColor: Color? = null,
    fontSize: TextUnit,
    shape: Shape,
    horizontalPadding: Dp,
    verticalPadding: Dp,
    isRipple: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val borderStroke = borderColor?.run { BorderStroke(1.dp, borderColor) }

    val vararg: ProvidedValue<*> = if (isRipple) {
        LocalRippleTheme provides CommonRippleTheme
    } else {
        LocalRippleTheme provides NoneRippleTheme
    }
    
    CompositionLocalProvider(vararg) {
        Button(
            onClick = onClick,
            border = borderStroke,
            shape = shape,
            modifier = modifier,
            elevation = null,
            enabled = isEnabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                disabledBackgroundColor = disabledBackgroundColor,
                disabledContentColor = disabledContentColor
            ),
            contentPadding = PaddingValues(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            )
        ) {
            CommonText(text = text, fontSize = fontSize, color = contentColor)
        }
    }
}

private object CommonRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha =  RippleTheme.defaultRippleAlpha(
        Color.Black,
        lightTheme = !isSystemInDarkTheme()
    )
}

private object NoneRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}