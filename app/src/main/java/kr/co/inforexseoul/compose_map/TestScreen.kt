package kr.co.inforexseoul.compose_map

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.co.inforexseoul.common_util.collectAsStateWithLifecycle
import kr.co.inforexseoul.core_data.state.Result

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun TestScreen(name: String, testViewModel: TestViewModel = viewModel()) {
    val result by testViewModel.uiState.collectAsStateWithLifecycle(Result.Loading)

    when (result) {
        is Result.Error -> Unit
        is Result.Loading -> Log.d("123123", "로딩")
        is Result.Success -> Log.d("123123", "성공")
    }

    if (result is Result.Loading) {
        LoadingBar()
    }

    Text(text = "Hello $name!")
}

@Composable
fun LoadingBar() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        color = MaterialTheme.colors.primary
    )
}