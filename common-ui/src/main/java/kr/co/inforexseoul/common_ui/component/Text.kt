package kr.co.inforexseoul.common_ui.component

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kr.co.inforexseoul.common_ui.UIConstants

@Composable
fun CommonText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = UIConstants.FONT_SIZE_MEDIUM.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = MaterialTheme.colors.onBackground,
    textAlign: TextAlign = TextAlign.Start,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        textAlign = textAlign,
        fontStyle = fontStyle,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style,
    )
}