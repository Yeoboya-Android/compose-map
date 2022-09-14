package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_network.datasource.TestRemoteDataSource
import kr.co.inforexseoul.core_network.service.model.CharacterInfo
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testRemoteDataSource: TestRemoteDataSource
) : TestRepository {
    override fun getCharacterInfoList(): Flow<List<CharacterInfo>> {
        return testRemoteDataSource.getCharacterInfoList()
    }
}