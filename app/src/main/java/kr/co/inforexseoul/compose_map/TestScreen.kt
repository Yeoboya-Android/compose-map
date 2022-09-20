package kr.co.inforexseoul.compose_map

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import kr.co.inforexseoul.common_model.test_model.TestDataModel
import kr.co.inforexseoul.common_util.ui.collectAsStateWithLifecycle
import kr.co.inforexseoul.core_data.state.Result

/**
 * TODO 테스트 화면
 * */
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun TestScreen(testViewModel: TestViewModel = viewModel()) {
    val result by testViewModel.uiState.collectAsStateWithLifecycle(Result.Loading)

    when (result) {
        is Result.Error -> Unit
        is Result.Loading -> LoadingBar()
        is Result.Success -> GridCardLayout((result as Result.Success<List<TestDataModel>>).data)
    }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridCardLayout(list: List<TestDataModel>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(10.dp)) {
        items(items = list, itemContent = { testDataModel ->
            TestCardView(testDataModel)
        })
    }
}

@Composable
fun TestCardView(testDataModel: TestDataModel) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            val painter = rememberAsyncImagePainter(testDataModel.sprite)

            Image(
                painter = painter,
                contentDescription = "Forest Image",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}