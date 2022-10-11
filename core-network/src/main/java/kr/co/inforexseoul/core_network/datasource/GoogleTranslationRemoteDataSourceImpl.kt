package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.inforexseoul.core_network.service.GoogleTranslationApiService
import javax.inject.Inject


class GoogleTranslationRemoteDataSourceImpl @Inject constructor(
    private val translationApiService: GoogleTranslationApiService
) : GoogleTranslationRemoteDataSource {
    override fun translateText(
        key: String,
        source: String,
        target: String,
        text: String
    ): Flow<String> {
        return translationApiService.translateText(
            key,
            source,
            target,
            text
        ).map {
            it.data.translations[0].translatedText
        }
    }
}