package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_network.service.model.CharacterInfo

interface TestRemoteDataSource {
    fun getCharacterInfoList(): Flow<List<CharacterInfo>>
}