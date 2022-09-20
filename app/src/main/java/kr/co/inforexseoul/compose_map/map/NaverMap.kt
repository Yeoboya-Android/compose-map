package kr.co.inforexseoul.compose_map.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import kr.co.inforexseoul.common_ui.theme.Compose_mapTheme

/**
 * 네이버 지도
 */
@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun OpenNaverMap() {
    Compose_mapTheme() {
        NaverMap(
            modifier = Modifier.fillMaxSize()
        )
    }
}