package kr.co.inforexseoul.core_network.service

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.OpenWeatherMapDataModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface OpenWeatherMapApiService {

    @GET("data/3.0/onecall")
    fun getOpenWeatherMapForecast(
        @Query("appid") appId: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String,
    ): Flow<OpenWeatherMapDataModel>
}