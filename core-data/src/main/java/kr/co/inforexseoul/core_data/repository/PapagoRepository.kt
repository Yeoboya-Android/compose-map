package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow

interface PapagoRepository {
    fun translateText(
        clientId: String,
        clientSecret: String,
        source: String,
        target: String,
        text: String
    ): Flow<String>
}