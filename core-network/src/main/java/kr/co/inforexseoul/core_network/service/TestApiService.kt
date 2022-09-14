package kr.co.inforexseoul.core_network.service

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_network.service.model.CharacterInfo
import retrofit2.http.GET

interface TestApiService {

    @GET("characters")
    fun getCharacterInfoList(): Flow<List<CharacterInfo>>

}