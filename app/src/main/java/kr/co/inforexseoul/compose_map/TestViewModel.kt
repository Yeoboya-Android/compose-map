package kr.co.inforexseoul.compose_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_data.usecase.GetTestDataUseCase
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    getTestDataUseCase: GetTestDataUseCase
) : ViewModel() {

    val uiState =
        getTestDataUseCase.invoke().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading
        )

}