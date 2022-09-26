package kr.co.inforexseoul.core_network.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.common_model.test_model.VillageForecastItems

interface VillageForecastRemoteDataSource {
    fun getVillageForecast(
        serviceKey: String,
        numOfRows: Int,
        pageNo: Int,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): Flow<VillageForecastItems>
}