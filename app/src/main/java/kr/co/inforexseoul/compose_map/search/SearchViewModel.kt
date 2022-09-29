package kr.co.inforexseoul.compose_map.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_data.usecase.FindDistrictNamesUseCase
import kr.co.inforexseoul.core_data.usecase.GetRecentSearchDistrictsUseCase
import kr.co.inforexseoul.core_data.usecase.UpdateAddRecentSearchDistrictUseCase
import kr.co.inforexseoul.core_data.usecase.UpdateDeleteRecentSearchDistrictUseCase
import kr.co.inforexseoul.core_database.entity.District
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    getRecentSearchDistrictsUseCase: GetRecentSearchDistrictsUseCase,
    private val findDistrictNamesUseCase: FindDistrictNamesUseCase,
    private val updateAddRecentSearchDistrictUseCase: UpdateAddRecentSearchDistrictUseCase,
    private val updateDeleteRecentSearchDistrictUseCase: UpdateDeleteRecentSearchDistrictUseCase,
) : ViewModel() {

    val recentSearchDistrictState = getRecentSearchDistrictsUseCase.invoke().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Result.Loading,
    )

    private val _keywordDistrictListState = MutableStateFlow<Result<List<District>>>(Result.Loading)
    val keywordDistrictListState = _keywordDistrictListState.asStateFlow()

    fun addRecentSearchDistrict(district: District) {
        updateAddRecentSearchDistrictUseCase.invoke(district).launchIn(viewModelScope)
    }

    fun deleteRecentSearchDistrict(district: District) {
        updateDeleteRecentSearchDistrictUseCase.invoke(district).launchIn(viewModelScope)
    }

    fun findLocation(word: String) {

    }

    fun findAddress(keyword: String) {
        findDistrictNamesUseCase.invoke(keyword)
            .debounce(1_000)
            .onEach { _keywordDistrictListState.value = it }
            .launchIn(viewModelScope)
    }
}