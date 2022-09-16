package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.TestDataModel
import kr.co.inforexseoul.core_network.datasource.TestRemoteDataSource
import javax.inject.Inject

/**
 * TODO 테스트용
 * */
class TestRepositoryImpl @Inject constructor(
    private val testRemoteDataSource: TestRemoteDataSource
) : TestRepository {
    override fun getCharacterInfoList(): Flow<List<TestDataModel>> {
        return testRemoteDataSource.getCharacterInfoList()
    }
}