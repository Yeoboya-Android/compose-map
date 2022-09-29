package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_network.datasource.PapagoRemoteDataSource
import javax.inject.Inject

class PapagoRepositoryImpl @Inject constructor(
    private val papagoRemoteDataSource: PapagoRemoteDataSource
) : PapagoRepository {
    override fun translateText(
        clientId: String,
        clientSecret: String,
        source: String,
        target: String,
        text: String
    ): Flow<String> {
        return papagoRemoteDataSource.translateText(
            clientId,
            clientSecret,
            source,
            target,
            text
        )
    }
}