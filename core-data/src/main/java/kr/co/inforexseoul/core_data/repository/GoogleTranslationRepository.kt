package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow

interface GoogleTranslationRepository {
    fun translateText(
        key: String,
        source: String,
        target: String,
        text: String
    ): Flow<String>
}