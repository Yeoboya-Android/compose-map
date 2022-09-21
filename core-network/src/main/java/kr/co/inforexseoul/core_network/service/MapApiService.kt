package kr.co.inforexseoul.core_network.service

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.BusStationInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApiService {

    // 버스 정류소 위치 정보 API
    @GET("json/stationInfo")
    fun getBusStationInfo(@Query("serviceKey") serviceKey : String) : Flow<BusStationInfo>

}