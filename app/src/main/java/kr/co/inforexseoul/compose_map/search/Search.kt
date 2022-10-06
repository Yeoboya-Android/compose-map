package kr.co.inforexseoul.compose_map.search

import SearchLayout
import SearchTextField
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.flowOf
import kr.co.inforexseoul.common_ui.DevicePreviews
import kr.co.inforexseoul.common_ui.theme.MainTheme
import kr.co.inforexseoul.core_database.entity.District

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchDialog(
    searchDialogOpen: MutableTransitionState<Boolean>,
    resultDistrict: (District) -> Unit
) {
    if (searchDialogOpen.targetState) {
        Dialog(
            onDismissRequest = { searchDialogOpen.targetState = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
                SearchRoute { searchDistrict ->
                    searchDialogOpen.targetState = false
                    resultDistrict.invoke(searchDistrict)
                }
            }
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchRoute(
    searchViewModel: SearchViewModel = viewModel(),
    result: (District) -> Unit
) {
    val keywordDistrictListState = searchViewModel.searchKeywordTextState
    val recentSearchList by
        searchViewModel.recentSearchDistrictState.collectAsStateWithLifecycle(listOf())

    SearchLayout(
        keywordDistrictListState = keywordDistrictListState,
        recentSearchList = recentSearchList,
        onDelete = { searchViewModel.deleteRecentSearchDistrict(it) },
        onResult = { district ->
            searchViewModel.addRecentSearchDistrict(district)
            result.invoke(district)
        },
        onUpdateKeyword = { searchViewModel.setKeyword(it) }
    )
}


/** Preview */
private val previewDistrictList = listOf(
    District(
        id = 1,
        districtId = 1100000000,
        districtName = "서울특별시",
        nx = 60,
        ny = 127,
        latitude = 37.56356944444444,
        longitude = 126.98000833333333,
        recentSearchYn = "n"
    ),
    District(
        id = 2,
        districtId = 1111000000,
        districtName = "서울특별시 종로구",
        nx = 60,
        ny = 127,
        latitude = 37.57037777777778,
        longitude = 126.98164166666668,
        recentSearchYn = "n"
    ),
    District(
        id = 3,
        districtId = 1111051500,
        districtName = "서울특별시 종로구 청운효자동",
        nx = 60,
        ny = 127,
        latitude = 37.584,
        longitude = 126.971,
        recentSearchYn = "n"
    ),
    District(
        id = 4,
        districtId = 1111053000,
        districtName = "서울특별시 종로구 사직동",
        nx = 60,
        ny = 127,
        latitude = 37.57326944444445,
        longitude = 126.97095555555556,
        recentSearchYn = "n"
    ),
    District(
        id = 5,
        districtId = 1111054000,
        districtName = "서울특별시 종로구 삼청동",
        nx = 60,
        ny = 127,
        latitude = 37.582425,
        longitude = 126.98397777777778,
        recentSearchYn = "n"
    ),
    District(
        id = 6,
        districtId = 1111055000,
        districtName = "서울특별시 종로구 부암동",
        nx = 60,
        ny = 127,
        latitude = 37.58985555555556,
        longitude = 126.96644444444445,
        recentSearchYn = "n"
    ),
    District(
        id = 7,
        districtId = 1111056000,
        districtName = "서울특별시 종로구 평창동",
        nx = 60,
        ny = 127,
        latitude = 37.60252222222223,
        longitude = 126.96887777777778,
        recentSearchYn = "n"
    ),
    District(
        id = 8,
        districtId = 1111057000,
        districtName = "서울특별시 종로구 무악동",
        nx = 60,
        ny = 127,
        latitude = 37.571538888888895,
        longitude = 126.96120833333333,
        recentSearchYn = "n"
    )
)

@Preview
@Composable
private fun SearchTextFieldPreview() {
    MainTheme {
        SearchTextField(placeholder = "입력")
    }
}

@DevicePreviews
@Composable
private fun SearchLayoutPreview() {
    MainTheme {
        SearchLayout(
            keywordDistrictListState = flowOf(PagingData.from(previewDistrictList)),
            recentSearchList = previewDistrictList,
            onResult = {},
            onUpdateKeyword = {},
            onDelete = {}
        )
    }
}