package kr.co.inforexseoul.compose_map.subtitle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kr.co.inforexseoul.compose_map.BuildConfig
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_data.usecase.GetClovaSubtitlesUseCase
import javax.inject.Inject

@HiltViewModel
class VideoSubtitlesViewModel @Inject constructor(
    getClovaSubtitlesUseCase: GetClovaSubtitlesUseCase
) : ViewModel() {

    val clovaSubtitlesState = getClovaSubtitlesUseCase.invoke(
        secretKey = BuildConfig.CLOVA_SPEECH_SECRET,
        url = "https://yonhapnewstv-prod.s3.ap-northeast-2.amazonaws.com/article/MYH/20221004/MYH20221004010200038_700M1.mp4",
        language = "ko-KR"
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = Result.Loading
    )
}
