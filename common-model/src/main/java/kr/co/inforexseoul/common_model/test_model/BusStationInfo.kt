package kr.co.inforexseoul.common_model.test_model

import com.google.gson.annotations.SerializedName

data class BusStationInfo (
    @SerializedName("STATION_LIST")
    val stationList : List<StationInfo>
)

data class StationInfo (
    @SerializedName("STATION_NUM")
    val stationNum: Long,       // 정류소 번호
    @SerializedName("BUSSTOP_NAME")
    val busStopName: String,    // 정류소 이름
    @SerializedName("NAME_E")
    val nameEN: String,          // 정류소 이름(영문)
    @SerializedName("LONGITUDE")
    val longitude: Double,      // 위도
    @SerializedName("LATITUDE")
    val latitude: Double        // 경도
)