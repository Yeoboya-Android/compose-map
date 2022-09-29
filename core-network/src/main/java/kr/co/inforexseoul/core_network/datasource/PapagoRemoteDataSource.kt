package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow

interface PapagoRemoteDataSource {
    fun translateText(
        clientId: String,
        clientSecret: String,
        source: String,
        target: String,
        text: String
    ): Flow<String>
}