package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.BusStationInfo

interface MapRemoteDataSource {
    fun getBusStationInfoList(serviceKey : String) : Flow<BusStationInfo>
}