package kr.co.inforexseoul.compose_map.translate

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.co.inforexseoul.common_ui.component.FilledButton
import kr.co.inforexseoul.common_ui.component.LoadingBar
import kr.co.inforexseoul.common_ui.component.StrokeButton
import kr.co.inforexseoul.compose_map.R

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TranslateScreen(
    translateViewModel: TranslateViewModel = viewModel(),
    appbarTitle: MutableState<@Composable () -> Unit>
) {

    val apiItemList = arrayListOf<String>().apply {
        add("Firebase ML Kit")
        add("Google Translation")
        add("Naver Papago")
    }

    val itemList = arrayListOf<Pair<String, String>>().apply {
        add(Pair(stringResource(R.string.translate_language_ko), "ko"))
        add(Pair(stringResource(R.string.translate_language_en), "en"))
        add(Pair(stringResource(R.string.translate_language_zh), "zh-CN"))
        add(Pair(stringResource(R.string.translate_language_ja), "ja"))
        add(Pair(stringResource(R.string.translate_language_fr), "fr"))
    }

    val apiText = remember { mutableStateOf(apiItemList[0]) }

    val sourceSelectorText = remember { mutableStateOf(itemList[0].first) }
    val targetSelectorText = remember { mutableStateOf(itemList[1].first) }

    val sourceLanguage = remember { mutableStateOf(itemList[0].second) }
    val targetLanguage = remember { mutableStateOf(itemList[1].second) }

    val sourceText = remember { mutableStateOf("") }
    val targetText = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val result by translateViewModel.translateState.collectAsStateWithLifecycle()
    when (result) {
        is TranslateState.UnInit -> Unit
        is TranslateState.Error -> Log.e("qwe123", "translate error")
        is TranslateState.Loading -> LoadingBar()
        is TranslateState.Success -> {
            targetText.value = (result as TranslateState.Success).data
        }
    }

    appbarTitle.value = { APISelector(apiText, apiItemList) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {

        LanguageSelector(
            sourceLanguage = sourceLanguage,
            targetLanguage = targetLanguage,
            sourceSelectorText = sourceSelectorText,
            targetSelectorText = targetSelectorText,
            sourceText = sourceText,
            targetText = targetText,
            itemList = itemList
        )

        TranslateTextField(
            sourceText = sourceText,
            targetText = targetText,
            sourceSelectorText = sourceSelectorText,
            targetSelectorText = targetSelectorText
        )

        Box(modifier = Modifier.weight(1f)) {
            FilledButton(
                isEnabled = sourceText.value.isNotEmpty(),
                text = stringResource(R.string.translate_button),
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                translateViewModel.translateText(
                    text = sourceText.value,
                    sourceLanguage = sourceLanguage.value,
                    targetLanguage = targetLanguage.value,
                    apiText = apiText.value
                )
                focusManager.clearFocus()
            }
        }
    }
}


@Composable
fun APISelector(apiText: MutableState<String>, itemList: ArrayList<String>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = (-32).dp)
    ) {
        var isExpanded by remember { mutableStateOf(false) }
        var buttonSize by remember { mutableStateOf(Size.Zero) }
        Text(
            text = apiText.value,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .onGloballyPositioned {
                    buttonSize = it.size.toSize()
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isExpanded = true
                }
        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { buttonSize.width.toDp() })
                .background(MaterialTheme.colors.background)
        ) {
            itemList.forEach {
                DropdownMenuItem(
                    onClick = {
                        apiText.value = it
                        isExpanded = false
                    }
                ) {
                    Text(it)
                }
            }
        }
    }
}

@Composable
fun LanguageSelector(
    sourceLanguage: MutableState<String>,
    targetLanguage: MutableState<String>,
    sourceSelectorText: MutableState<String>,
    targetSelectorText: MutableState<String>,
    sourceText: MutableState<String>,
    targetText: MutableState<String>,
    itemList: ArrayList<Pair<String, String>>,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        var isSourceLanguageExpanded by remember { mutableStateOf(false) }
        var isTargetLanguageExpanded by remember { mutableStateOf(false) }

        Row(modifier = Modifier.weight(1f)) {
            var buttonSize by remember { mutableStateOf(Size.Zero) }
            StrokeButton(
                text = sourceSelectorText.value,
                modifier = Modifier
                    .weight(1f)
                    .onGloballyPositioned {
                        buttonSize = it.size.toSize()
                    }
            ) {
                isSourceLanguageExpanded = true
            }
            DropdownMenu(
                expanded = isSourceLanguageExpanded,
                onDismissRequest = { isSourceLanguageExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { buttonSize.width.toDp() })
                    .background(MaterialTheme.colors.background)
            ) {
                itemList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            sourceSelectorText.value = it.first
                            sourceLanguage.value = it.second
                            isSourceLanguageExpanded = false
                        }
                    ) {
                        Text(it.first)
                    }
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ico_arrows),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(30.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    val copySourceSelectorText = sourceSelectorText.value
                    val copyTargetSelectorText = targetSelectorText.value
                    val copySourceLanguage = sourceLanguage.value
                    val copyTargetLanguage = targetLanguage.value
                    sourceSelectorText.value = copyTargetSelectorText
                    targetSelectorText.value = copySourceSelectorText
                    sourceLanguage.value = copyTargetLanguage
                    targetLanguage.value = copySourceLanguage
                    sourceText.value = targetText.value
                },
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
        )

        Row(modifier = Modifier.weight(1f)) {
            var buttonSize by remember { mutableStateOf(Size.Zero) }
            StrokeButton(
                text = targetSelectorText.value,
                modifier = Modifier
                    .weight(1f)
                    .onGloballyPositioned {
                        buttonSize = it.size.toSize()
                    }
            ) {
                isTargetLanguageExpanded = true
            }
            DropdownMenu(
                expanded = isTargetLanguageExpanded,
                onDismissRequest = { isTargetLanguageExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { buttonSize.width.toDp() })
                    .background(MaterialTheme.colors.background)
            ) {
                itemList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            targetSelectorText.value = it.first
                            targetLanguage.value = it.second
                            isTargetLanguageExpanded = false
                        }
                    ) {
                        Text(it.first)
                    }
                }
            }

        }
    }
}

@Composable
fun TranslateTextField(
    sourceText: MutableState<String>,
    targetText: MutableState<String>,
    sourceSelectorText: MutableState<String>,
    targetSelectorText: MutableState<String>,
) {
    Column {
        OutlinedTextField(
            value = sourceText.value,
            onValueChange = { sourceText.value = it },
            label = { Text(text = sourceSelectorText.value) },
            trailingIcon = {
                if (sourceText.value.isNotEmpty())
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                sourceText.value = ""
                            }
                    )
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = targetText.value,
            onValueChange = { targetText.value = it },
            readOnly = true,
            label = { Text(text = targetSelectorText.value)},
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}