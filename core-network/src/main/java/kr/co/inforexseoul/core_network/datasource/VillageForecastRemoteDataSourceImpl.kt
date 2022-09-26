package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.inforexseoul.common_model.test_model.VillageForecastItems
import kr.co.inforexseoul.core_network.service.VillageForecastApiService
import javax.inject.Inject


class VillageForecastRemoteDataSourceImpl @Inject constructor(
    private val villageForecastApiService: VillageForecastApiService
) : VillageForecastRemoteDataSource {
    override fun getVillageForecast(
        serviceKey: String,
        numOfRows: Int,
        pageNo: Int,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): Flow<VillageForecastItems> {
        return villageForecastApiService.getVillageForecast(
            serviceKey,
            numOfRows,
            pageNo,
            baseDate,
            baseTime,
            nx,
            ny
        ).map { it.response.body.items }
    }
}