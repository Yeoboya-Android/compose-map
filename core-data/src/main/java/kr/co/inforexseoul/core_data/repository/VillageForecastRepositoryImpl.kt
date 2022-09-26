package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.VillageForecastItems
import kr.co.inforexseoul.core_network.datasource.VillageForecastRemoteDataSource
import javax.inject.Inject

class VillageForecastRepositoryImpl @Inject constructor(
    private val villageForecastRemoteDataSource: VillageForecastRemoteDataSource
) : VillageForecastRepository {
    override fun getVillageForecast(
        serviceKey: String,
        numOfRows: Int,
        pageNo: Int,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): Flow<VillageForecastItems> {
        return villageForecastRemoteDataSource.getVillageForecast(
            serviceKey,
            numOfRows,
            pageNo,
            baseDate,
            baseTime,
            nx,
            ny
        )
    }
}