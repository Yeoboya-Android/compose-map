package kr.co.inforexseoul.compose_map

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import jxl.Workbook
import jxl.read.biff.BiffException
import kr.co.inforexseoul.common_ui.component.FilledButton
import kr.co.inforexseoul.common_ui.theme.MainTheme
import kr.co.inforexseoul.compose_map.map.MapScreen
import kr.co.inforexseoul.compose_map.weather.WeatherView
import java.io.IOException

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readLocalExcel()
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


    private fun readLocalExcel() {
        try {
            val inputStream = resources.assets.open("local.xls")
            val workbook = Workbook.getWorkbook(inputStream)
            val sheet = workbook.getSheet(0)

            val colTotalCount = sheet.columns
            val rowTotalCount = sheet.getColumn(colTotalCount-1).size
            Log.d("qwe123", "MainActivity.readLocalExcel()::: colTotalCount: $colTotalCount, rowTotalCount: $rowTotalCount")
            for (i in 1..10) {
                val printText = sheet.getRow(i)[4].contents
                Log.d("qwe123", "MainActivity.readLocalExcel()::: printText: $printText")
            }
        } catch (e: Exception) {
            Log.e("qwe123", "MainActivity.readLocalExcel()::: e: ${e.stackTraceToString()}")
            if (e is IOException || e is BiffException) e.printStackTrace()
            else throw e
        }
    }
}

@Composable
private fun TestWeatherLayout() {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
        val dialogOpen = remember { MutableTransitionState(false) }

        FilledButton(text = "날씨", modifier = Modifier.align(Alignment.Center)) {
            dialogOpen.targetState = true
        }
        WeatherView(open = dialogOpen)
    }
}