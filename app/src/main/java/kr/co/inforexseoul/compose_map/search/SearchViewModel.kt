package kr.co.inforexseoul.compose_map.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import kr.co.inforexseoul.common_util.di.IoDispatcher
import kr.co.inforexseoul.core_data.di.PagingSourceModule
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_data.usecase.GetPageDistrictUseCase
import kr.co.inforexseoul.core_data.usecase.GetRecentSearchDistrictsUseCase
import kr.co.inforexseoul.core_data.usecase.UpdateAddRecentSearchDistrictUseCase
import kr.co.inforexseoul.core_data.usecase.UpdateDeleteRecentSearchDistrictUseCase
import kr.co.inforexseoul.core_database.entity.District
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    getRecentSearchDistrictsUseCase: GetRecentSearchDistrictsUseCase,
    private val updateAddRecentSearchDistrictUseCase: UpdateAddRecentSearchDistrictUseCase,
    private val updateDeleteRecentSearchDistrictUseCase: UpdateDeleteRecentSearchDistrictUseCase,
    private val getPageDistrictUseCase: GetPageDistrictUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    val recentSearchDistrictState = getRecentSearchDistrictsUseCase.invoke().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Result.Loading,
    ).map { state ->
        if (state is Result.Success) {
            state.data
        } else {
            listOf()
        }
    }

    private val _searchKeywordTextState = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchKeywordTextState = _searchKeywordTextState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ""
    ).debounce(1_000).flatMapLatest { keyword ->
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PagingSourceModule.providesDistrictPagingSource(
                    keyword = keyword,
                    getPageDistrictUseCase = getPageDistrictUseCase
                )
            }
        ).flow.cachedIn(viewModelScope.plus(ioDispatcher))
    }

    fun addRecentSearchDistrict(district: District) {
        updateAddRecentSearchDistrictUseCase.invoke(district).launchIn(viewModelScope)
    }

    fun deleteRecentSearchDistrict(district: District) {
        updateDeleteRecentSearchDistrictUseCase.invoke(district).launchIn(viewModelScope)
    }

    fun setKeyword(keyword: String) {
        _searchKeywordTextState.value = keyword
    }
}