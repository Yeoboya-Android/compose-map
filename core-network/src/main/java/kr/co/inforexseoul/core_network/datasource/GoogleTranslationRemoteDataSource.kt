package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow

interface GoogleTranslationRemoteDataSource {
    fun translateText(
        key: String,
        source: String,
        target: String,
        text: String
    ): Flow<String>
}