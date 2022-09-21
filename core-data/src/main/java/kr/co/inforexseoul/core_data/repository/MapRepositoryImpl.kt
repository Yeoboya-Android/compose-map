package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.BusStationInfo
import kr.co.inforexseoul.core_network.datasource.MapRemoteDataSource
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapRemoteDataSource: MapRemoteDataSource
) : MapRepository {
    override fun getBusStationInfoList(serviceKey : String): Flow<BusStationInfo> {
        return mapRemoteDataSource.getBusStationInfoList(serviceKey)
    }
}