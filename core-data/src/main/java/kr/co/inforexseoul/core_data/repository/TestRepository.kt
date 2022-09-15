package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_network.service.model.CharacterInfo

interface TestRepository {
    fun getCharacterInfoList(): Flow<List<CharacterInfo>>
}