package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.BusStationInfo

interface MapRepository {
    fun getBusStationInfoList(serviceKey : String) : Flow<BusStationInfo>
}