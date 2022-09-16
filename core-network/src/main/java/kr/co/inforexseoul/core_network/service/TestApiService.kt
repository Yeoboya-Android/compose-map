package kr.co.inforexseoul.core_network.service

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.TestDataModel
import retrofit2.http.GET

/**
 * TODO 테스트용
 * */
interface TestApiService {

    @GET("characters")
    fun getCharacterInfoList(): Flow<List<TestDataModel>>

}