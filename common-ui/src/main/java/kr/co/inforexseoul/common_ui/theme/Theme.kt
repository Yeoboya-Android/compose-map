package kr.co.inforexseoul.common_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Mint80,
    primaryVariant = Mint20,
    onPrimary = Mint10,
    secondary = Mint90,
    secondaryVariant = Mint30,
    onSecondary = Mint20,
    background = Grey20,
    onBackground = Grey99,
    surface = Grey20,
    onSurface = Grey99,
    error = Red80,
    onError = Red20,
)

private val LightColorPalette = lightColors(
    primary = Mint20,
    primaryVariant = Mint10,
    onPrimary = Color.White,
    secondary = Mint30,
    secondaryVariant = Mint10,
    onSecondary = Color.White,
    background = Color.White,
    onBackground = Grey10,
    surface = Grey95,
    onSurface = Grey10,
    error = Red30,
    onError = Color.White,
)

@Composable
fun MainTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}