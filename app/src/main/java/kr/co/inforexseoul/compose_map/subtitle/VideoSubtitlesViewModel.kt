package kr.co.inforexseoul.compose_map.subtitle

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kr.co.inforexseoul.common_model.test_model.ClovaSpeechDataModel
import kr.co.inforexseoul.compose_map.BuildConfig
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_data.usecase.GetClovaSubtitlesUseCase
import kr.co.inforexseoul.core_data.usecase.GetClovaSubtitlesWithUploadUseCase
import java.io.File
import javax.inject.Inject

@HiltViewModel
class VideoSubtitlesViewModel @Inject constructor(
    getClovaSubtitlesUseCase: GetClovaSubtitlesUseCase,
    private val getClovaSubtitlesWithUploadUseCase: GetClovaSubtitlesWithUploadUseCase
) : ViewModel() {

    private val _videoUploadState = MutableStateFlow<Result<ClovaSpeechDataModel>>(Result.Loading)
    var videoUploadState = _videoUploadState

    fun uploadVideo(file: File) {
        Log.d("qwe123", "VideoSubtitlesViewModel.uploadVideo()::: path: ${file.path}, exists: ${file.exists()}")
        getClovaSubtitlesWithUploadUseCase.invoke(
            secretKey = BuildConfig.CLOVA_SPEECH_SECRET,
            file = file,
            language = "ko-KR"
        ).onEach {
            _videoUploadState.value = it
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Result.Loading
        )
    }

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
