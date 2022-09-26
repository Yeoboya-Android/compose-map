package kr.co.inforexseoul.core_network.service

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.VillageForecastResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface VillageForecastApiService {

    @GET("1360000/VilageFcstInfoService_2.0/getUltraSrtFcst")
    fun getVillageForecast(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
        @Query("dataType") dataType: String = "JSON",
    ): Flow<VillageForecastResponseModel>
}