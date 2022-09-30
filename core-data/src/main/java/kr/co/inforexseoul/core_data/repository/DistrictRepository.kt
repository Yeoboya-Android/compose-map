package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_database.entity.District

interface DistrictRepository {

    fun getDistrictCount(): Flow<Int>
    suspend fun addDistrict(districtList: List<District>)
    fun getDistrict(latitude: Double, longitude: Double): Flow<District>
    fun getRecentSearchDistricts(): Flow<List<District>>
    fun updateAddRecentSearchDistricts(district: District): Flow<Unit>
    fun updateDeleteRecentSearchDistrict(district: District): Flow<Unit>
    fun getDistrictPageNames(keyword: String, index: Int, loadSize: Int): List<District>

}