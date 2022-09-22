package kr.co.inforexseoul.common_ui.component

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomToggleGroup(
    options : List<String>,
    style : TextStyle = MaterialTheme.typography.body1.merge(),
    cornerShape : Shape = RoundedCornerShape(12.dp),
    paddingVertical : Dp = 12.dp,
    paddingHorizontal : Dp = 16.dp,
    @ColorRes contentColor : Color = Color.White,
    @ColorRes selectedColor : Color = MaterialTheme.colors.primary,
    @ColorRes unSelectedColor : Color = Color.LightGray,
    modifier: Modifier = Modifier,
    clickable : (String) -> Unit
) {
    var selectedOption by remember { mutableStateOf(options[0]) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        options.forEach { text ->
            Row {
                Text(
                    text = text,
                    style = style,
                    color = contentColor,
                    modifier = Modifier
                        .clip(shape = cornerShape)
                        .clickable {
                            selectedOption = text
                            clickable.invoke(text)
                        }
                        .background(
                            if (text == selectedOption) {
                                selectedColor
                            } else {
                                unSelectedColor
                            }
                        )
                        .padding(
                            vertical = paddingVertical,
                            horizontal = paddingHorizontal
                        )
                )
            }
        }
    }
}