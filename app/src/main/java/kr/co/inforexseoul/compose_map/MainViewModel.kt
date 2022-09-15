package kr.co.inforexseoul.compose_map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.co.inforexseoul.core_data.repository.TestRepository
import kr.co.inforexseoul.core_data.usecase.GetTestDataUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetTestDataUseCase
) : ViewModel() {
    fun init() {
        useCase.invoke().onEach {
            Log.d("123123", "it..: $it")
        }.launchIn(viewModelScope)
    }
}