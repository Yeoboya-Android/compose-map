package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.BusStationInfo
import kr.co.inforexseoul.core_network.service.MapApiService
import javax.inject.Inject

class MapRemoteDataSourceImpl @Inject constructor(
    private val mapApiService : MapApiService
) : MapRemoteDataSource {
    override fun getBusStationInfoList(serviceKey : String): Flow<BusStationInfo> {
        return mapApiService.getBusStationInfo(serviceKey)
    }
}