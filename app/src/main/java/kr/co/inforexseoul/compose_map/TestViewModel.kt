package kr.co.inforexseoul.compose_map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kr.co.inforexseoul.common_util.di.DefaultDispatcher
import kr.co.inforexseoul.common_util.di.IoDispatcher
import kr.co.inforexseoul.common_util.di.MainImmediateDispatcher
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_data.usecase.GetTestDataUseCase
import javax.inject.Inject

/**
 * TODO 테스트용 ViewModel
 * */
@HiltViewModel
class TestViewModel @Inject constructor(
    getTestDataUseCase: GetTestDataUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @MainImmediateDispatcher private val mainImmediateDispatcher: CoroutineDispatcher,
) : ViewModel() {

    val uiState =
        getTestDataUseCase.invoke().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading
        )

    private val testFlow: Flow<Int> get() = flow {
        (0..10).onEach { emit(it) }
    }.onEach {
        Log.d("123123", "default: it: $it, name: ${Thread.currentThread().name}")
    }.flowOn(defaultDispatcher).onEach {
        Log.d("123123", "io: it: $it, name: ${Thread.currentThread().name}")
    }.flowOn(ioDispatcher).onEach {
        Log.d("123123", "main: it: $it, name: ${Thread.currentThread().name}")
    }.flowOn(mainImmediateDispatcher)

    init {
        testFlow.launchIn(viewModelScope)
    }

}