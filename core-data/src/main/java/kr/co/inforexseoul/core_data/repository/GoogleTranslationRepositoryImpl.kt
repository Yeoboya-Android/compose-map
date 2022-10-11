package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_network.datasource.GoogleTranslationRemoteDataSource
import javax.inject.Inject

class GoogleTranslationRepositoryImpl @Inject constructor(
    private val googleTranslationRemoteDataSource: GoogleTranslationRemoteDataSource
) : GoogleTranslationRepository {
    override fun translateText(
        key: String,
        source: String,
        target: String,
        text: String
    ): Flow<String> {
        return googleTranslationRemoteDataSource.translateText(
            key,
            source,
            target,
            text
        )
    }
}