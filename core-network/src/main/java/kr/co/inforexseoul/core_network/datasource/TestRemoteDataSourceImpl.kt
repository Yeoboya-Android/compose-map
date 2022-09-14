package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_network.service.TestApiService
import kr.co.inforexseoul.core_network.service.model.CharacterInfo
import javax.inject.Inject

class TestRemoteDataSourceImpl @Inject constructor(
    private val testApiService: TestApiService
) : TestRemoteDataSource {
    override fun getCharacterInfoList(): Flow<List<CharacterInfo>> {
        return testApiService.getCharacterInfoList()
    }
}