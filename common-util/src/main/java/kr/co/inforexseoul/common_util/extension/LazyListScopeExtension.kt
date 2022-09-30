package kr.co.inforexseoul.common_util.extension

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items

fun <T : Any> LazyListScope.group(
    titleDecorator: TitleDecorator,
    items: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {
    item {
        Text(
            text = titleDecorator.text,
            modifier = titleDecorator.modifier,
            color = titleDecorator.color,
            fontSize = titleDecorator.fontSize,
            fontStyle = titleDecorator.fontStyle,
            fontWeight = titleDecorator.fontWeight,
            fontFamily = titleDecorator.fontFamily,
            letterSpacing = titleDecorator.letterSpacing,
            textDecoration = titleDecorator.textDecoration,
            textAlign = titleDecorator.textAlign,
            lineHeight = titleDecorator.lineHeight,
            overflow = titleDecorator.overflow,
            softWrap = titleDecorator.softWrap,
            maxLines = titleDecorator.maxLines,
            onTextLayout = titleDecorator.onTextLayout,
        )
    }
    items(items = items, itemContent = itemContent)
}

fun <T : Any> LazyListScope.group(
    titleDecorator: TitleDecorator,
    items: List<T>,
    itemContent: @Composable LazyItemScope.(value: T) -> Unit
) {
    item {
        Text(
            text = titleDecorator.text,
            modifier = titleDecorator.modifier,
            color = titleDecorator.color,
            fontSize = titleDecorator.fontSize,
            fontStyle = titleDecorator.fontStyle,
            fontWeight = titleDecorator.fontWeight,
            fontFamily = titleDecorator.fontFamily,
            letterSpacing = titleDecorator.letterSpacing,
            textDecoration = titleDecorator.textDecoration,
            textAlign = titleDecorator.textAlign,
            lineHeight = titleDecorator.lineHeight,
            overflow = titleDecorator.overflow,
            softWrap = titleDecorator.softWrap,
            maxLines = titleDecorator.maxLines,
            onTextLayout = titleDecorator.onTextLayout,
        )
    }
    items(items = items, itemContent = itemContent)
}

data class TitleDecorator(
    val text: String,
    val modifier: Modifier = Modifier,
    val color: Color = Color.Unspecified,
    val fontSize: TextUnit = TextUnit.Unspecified,
    val fontStyle: FontStyle? = null,
    val fontWeight: FontWeight? = null,
    val fontFamily: FontFamily? = null,
    val letterSpacing: TextUnit = TextUnit.Unspecified,
    val textDecoration: TextDecoration? = null,
    val textAlign: TextAlign? = null,
    val lineHeight: TextUnit = TextUnit.Unspecified,
    val overflow: TextOverflow = TextOverflow.Clip,
    val softWrap: Boolean = true,
    val maxLines: Int = Int.MAX_VALUE,
    val onTextLayout: (TextLayoutResult) -> Unit = {},
)