package kr.co.inforexseoul.compose_map.subtitle

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechSegment
import kr.co.inforexseoul.compose_map.BuildConfig
import kr.co.inforexseoul.core_data.usecase.GetClovaSubtitlesUseCase
import kr.co.inforexseoul.core_data.usecase.GetClovaSubtitlesWithUploadUseCase
import java.io.File
import javax.inject.Inject

@HiltViewModel
class VideoSubtitlesViewModel @Inject constructor(
    getClovaSubtitlesUseCase: GetClovaSubtitlesUseCase,
    private val getClovaSubtitlesWithUploadUseCase: GetClovaSubtitlesWithUploadUseCase
) : ViewModel() {

    private val _videoUploadState = MutableStateFlow<SubtitleState>(SubtitleState.UnInit)
    var videoUploadState = _videoUploadState

    private val _testState = MutableStateFlow<SubtitleState>(SubtitleState.UnInit)
    val testState = _testState

    fun testUploadVideo(file: File) {
        viewModelScope.launch {
            _testState.value = SubtitleState.Loading
            delay(2000)
            val segmentArray = listOf(
                ClovaSpeechSegment(start = 0, end = 1000, text = "test1"),
                ClovaSpeechSegment(start = 1200, end = 2000, text = "test2"),
                ClovaSpeechSegment(start = 2200, end = 3000, text = "test3"),
                ClovaSpeechSegment(start = 3200, end = 4000, text = "test4"),
                ClovaSpeechSegment(start = 4200, end = 5000, text = "test5"),
                ClovaSpeechSegment(start = 5200, end = 6000, text = "test6"),
            )
            val data = ClovaSpeechDataModel(segments = segmentArray)
            _testState.value = SubtitleState.Success(data)
        }
    }

    fun uploadVideo(file: File) {
        getClovaSubtitlesWithUploadUseCase.invoke(
            secretKey = BuildConfig.CLOVA_SPEECH_SECRET,
            file = file,
            language = "ko-KR"
        )
        .onStart {
            _videoUploadState.value = SubtitleState.Loading
        }
        .onEach {
            _videoUploadState.value = SubtitleState.Success(it)
        }
        .launchIn(viewModelScope)
    }

    val clovaSubtitlesState = getClovaSubtitlesUseCase.invoke(
        secretKey = BuildConfig.CLOVA_SPEECH_SECRET,
        url = "https://yonhapnewstv-prod.s3.ap-northeast-2.amazonaws.com/article/MYH/20221004/MYH20221004010200038_700M1.mp4",
        language = "ko-KR"
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = SubtitleState.Loading
    )
}

sealed interface SubtitleState {
    object UnInit : SubtitleState
    object Loading : SubtitleState
    data class Success(val data: ClovaSpeechDataModel) : SubtitleState
}
