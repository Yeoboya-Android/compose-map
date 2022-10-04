import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kr.co.inforexseoul.common_util.extension.TitleDecorator
import kr.co.inforexseoul.common_util.extension.group
import kr.co.inforexseoul.common_util.permission.CheckPermission
import kr.co.inforexseoul.common_util.permission.recordPermissions
import kr.co.inforexseoul.common_util.ui.rememberKeyboardVisibleAsState
import kr.co.inforexseoul.compose_map.R
import kr.co.inforexseoul.compose_map.stt.SpeechRecognizerDialog
import kr.co.inforexseoul.core_database.entity.District

@Composable
fun SearchLayout(
    keywordDistrictListState: Flow<PagingData<District>> = flowOf(),
    recentSearchList: List<District> = listOf(),
    onResult: (District) -> Unit,
    onUpdateKeyword: (String) -> Unit,
    onDelete: (District) -> Unit
) {
    val text = remember { mutableStateOf("") }
    val speechRecognizerDialogOpen = remember { MutableTransitionState(false) }

    Surface(color = Color.White) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                /* STT 버튼 */
                SttButton(speechRecognizerDialogOpen)

                /* 검색 Input Text */
                SearchTextField(text = text, placeholder = "입력", modifier = Modifier.weight(3f))

                /* 검색 Input Text Clear 버튼 */
                ClearButton(visible = text.value.isNotEmpty(), onClick = { text.value = "" })
            }

            /* 검색 결과 리스트 */
            SearchResultList(
                text = text,
                keywordDistrictListState = keywordDistrictListState,
                onUpdateKeyword = onUpdateKeyword,
                onClick = onResult
            )

            /* 최근 검색 리스트 */
            RecentSearchList(
                recentSearchList = recentSearchList,
                onClick = onResult,
                onDelete = onDelete
            )
        }
    }

    if (speechRecognizerDialogOpen.targetState) {
        CheckPermission(permissions = recordPermissions) {
            SpeechRecognizerDialog(speechRecognizerDialogOpen) { text.value = it }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    text: MutableState<String> = mutableStateOf(""),
    placeholder: String = "",
    modifier: Modifier = Modifier
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardAsState by rememberKeyboardVisibleAsState()

    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        placeholder = { Text(placeholder) },
        singleLine = true,
        keyboardActions = KeyboardActions { keyboardController?.hide() },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier.focusRequester(focusRequester)
    )

    LaunchedEffect(Unit) {
        delay(100)
        keyboardController?.show()
        focusRequester.requestFocus()
    }

    if (!keyboardAsState) focusManager.clearFocus()
}

@Composable
fun RowScope.ClearButton(visible: Boolean = false, onClick: () -> Unit) {
    if (visible) {
        IconButton(onClick = onClick) {
            val clearIcon = painterResource(R.mipmap.ic_launcher_clear)
            Image(
                painter = clearIcon,
                contentDescription = "Clear Text",
                modifier = Modifier
                    .weight(1f)
                    .width(20.dp)
                    .height(20.dp)
            )
        }
    }
}

@Composable
fun RowScope.SttButton(speechRecognizerDialogOpen: MutableTransitionState<Boolean>) {
    IconButton(onClick = { speechRecognizerDialogOpen.targetState = true }) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_mic),
            contentDescription = "Forest Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f)
                .size(50.dp)
        )
    }
}

@Composable
fun RecentSearchList(
    recentSearchList: List<District> = listOf(),
    onClick: (District) -> Unit,
    onDelete: (District) -> Unit
) {
    LazyColumn {
        group(
            titleDecorator = makeSearchGroupTitleDecorator("최근 검색"),
            items = recentSearchList
        ) { district ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(30.dp, Dp.Infinity),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = district.districtName,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 15.dp)
                        .clickable { onClick.invoke(district) },
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                )
                IconButton(onClick = { onDelete.invoke(district) }) {
                    Image(
                        painter = painterResource(R.drawable.ic_close_b),
                        contentDescription = "delete search",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchResultList(
    text: State<String>,
    keywordDistrictListState: Flow<PagingData<District>>,
    onUpdateKeyword: (String) -> Unit,
    onClick: (District) -> Unit
) {
    val keywordDistrictList = keywordDistrictListState.collectAsLazyPagingItems()

    if (text.value.isNotEmpty()) {
        onUpdateKeyword.invoke(text.value)
        LazyColumn(verticalArrangement = Arrangement.Center) {
            group(
                titleDecorator = makeSearchGroupTitleDecorator("검색결과"),
                items = keywordDistrictList
            ) { district ->
                if (district != null) {
                    Text(
                        text = district.districtName,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .clickable { onClick.invoke(district) }
                    )
                }
            }
        }
    }
}

private fun makeSearchGroupTitleDecorator(title: String): TitleDecorator = TitleDecorator(
    text = title,
    fontSize = 20.sp,
    fontStyle = FontStyle.Italic,
)