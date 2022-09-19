package kr.co.inforexseoul.common_ui.componets

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.co.inforexseoul.common_ui.UIConstants


@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    fontSize: TextUnit = UIConstants.FONT_SIZE_MEDIUM.sp,
    radius: Dp = UIConstants.BUTTON_RADIUS.dp,
    horizontalPadding: Dp = UIConstants.BUTTON_HORIZONTAL_PADDING.dp,
    verticalPadding: Dp = UIConstants.BUTTON_VERTICAL_PADDING.dp,
    isRipple: Boolean = true,
    onClick: () -> Unit
) {
    CommonButton(
        modifier = modifier,
        text = text,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        borderColor = null,
        fontSize = fontSize,
        radius = radius,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
        isRipple = isRipple,
        onClick = onClick
    )
}

@Composable
fun StrokeButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.primary,
    borderColor: Color = MaterialTheme.colors.primary,
    fontSize: TextUnit = UIConstants.FONT_SIZE_MEDIUM.sp,
    radius: Dp = UIConstants.BUTTON_RADIUS.dp,
    horizontalPadding: Dp = UIConstants.BUTTON_HORIZONTAL_PADDING.dp,
    verticalPadding: Dp = UIConstants.BUTTON_VERTICAL_PADDING.dp,
    isRipple: Boolean = true,
    onClick: () -> Unit
) {
    CommonButton(
        modifier = modifier,
        text = text,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        borderColor = borderColor,
        fontSize = fontSize,
        radius = radius,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
        isRipple = isRipple,
        onClick = onClick
    )
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colors.onBackground,
    fontSize: TextUnit = UIConstants.FONT_SIZE_MEDIUM.sp,
    radius: Dp = 0.dp,
    horizontalPadding: Dp = UIConstants.BUTTON_HORIZONTAL_PADDING.dp,
    verticalPadding: Dp = UIConstants.BUTTON_VERTICAL_PADDING.dp,
    isRipple: Boolean = false,
    onClick: () -> Unit
) {
    CommonButton(
        modifier = modifier,
        text = text,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        borderColor = null,
        fontSize = fontSize,
        radius = radius,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
        isRipple = isRipple,
        onClick = onClick
    )
}

@Composable
private fun CommonButton(
    modifier: Modifier,
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    borderColor: Color? = null,
    fontSize: TextUnit,
    radius: Dp,
    horizontalPadding: Dp,
    verticalPadding: Dp,
    isRipple: Boolean,
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
            shape = RoundedCornerShape(radius),
            modifier = modifier,
            elevation = null,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = contentColor
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