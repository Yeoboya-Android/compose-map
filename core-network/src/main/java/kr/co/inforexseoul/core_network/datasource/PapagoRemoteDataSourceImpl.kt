package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.inforexseoul.core_network.service.PapagoApiService
import javax.inject.Inject


class PapagoRemoteDataSourceImpl @Inject constructor(
    private val papagoApiService: PapagoApiService
) : PapagoRemoteDataSource {
    override fun translateText(
        clientId: String,
        clientSecret: String,
        source: String,
        target: String,
        text: String
    ): Flow<String> {
        return papagoApiService.translateText(
            clientId,
            clientSecret,
            source,
            target,
            text
        ).map {
            it.message.result.translatedText
        }
    }
}