package kr.co.inforexseoul.compose_map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import kr.co.inforexseoul.common_ui.component.FilledButton
import kr.co.inforexseoul.common_ui.theme.MainTheme
import kr.co.inforexseoul.compose_map.map.MapScreen
import kr.co.inforexseoul.compose_map.weather.WeatherView

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MapScreen()
                    TestWeatherLayout()
                }
            }
        }
    }
}

@Composable
private fun TestWeatherLayout() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent)
    ) {
        val dialogOpen = remember { MutableTransitionState(false) }

        FilledButton(
            text = "날씨",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterEnd)
        ) {
            dialogOpen.targetState = true
        }
        WeatherView(open = dialogOpen, isGoogleMap = true)
    }
}