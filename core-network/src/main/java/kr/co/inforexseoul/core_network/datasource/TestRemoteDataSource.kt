package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.TestDataModel

/**
 * @TODO 테스트용
 * */
interface TestRemoteDataSource {
    fun getCharacterInfoList(): Flow<List<TestDataModel>>
}