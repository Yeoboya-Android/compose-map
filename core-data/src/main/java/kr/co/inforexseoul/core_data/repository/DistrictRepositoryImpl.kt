package kr.co.inforexseoul.core_data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.inforexseoul.core_database.dao.DistrictDao
import kr.co.inforexseoul.core_database.entity.District
import javax.inject.Inject

class DistrictRepositoryImpl @Inject constructor(
    private val districtDao: DistrictDao
) : DistrictRepository {

    override fun getDistrictCount(): Flow<Int> {
        return districtDao.selectDistrictCount()
    }

    override suspend fun addDistrict(districtList: List<District>) {
        return districtDao.insertDistrictList(districtList)
    }

    override fun getDistrict(latitude: Double, longitude: Double): Flow<District> {
        return districtDao.selectDistrict(latitude, longitude)
    }

    override fun findDistrictNames(word: String): Flow<List<District>> {
        return districtDao.findDistrictNames(word)
    }

    override fun getRecentSearchDistricts(): Flow<List<District>> {
        return districtDao.selectRecentSearchDistricts()
    }

    override fun updateAddRecentSearchDistricts(district: District): Flow<Unit> = flow {
        emit(districtDao.updateDistrict(district))
    }

    override fun updateDeleteRecentSearchDistrict(district: District): Flow<Unit> = flow {
        emit(districtDao.updateDistrict(district))
    }

}