package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.TestDataModel
import kr.co.inforexseoul.core_network.service.TestApiService
import javax.inject.Inject

/**
 * TODO 테스트용
 * */
class TestRemoteDataSourceImpl @Inject constructor(
    private val testApiService: TestApiService
) : TestRemoteDataSource {
    override fun getCharacterInfoList(): Flow<List<TestDataModel>> {
        return testApiService.getCharacterInfoList()
    }
}