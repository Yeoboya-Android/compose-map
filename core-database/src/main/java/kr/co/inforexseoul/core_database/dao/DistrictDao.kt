package kr.co.inforexseoul.core_database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_database.entity.District

@Dao
interface DistrictDao {

    @Query("SELECT COUNT(*) FROM District")
    fun selectDistrictCount(): Flow<Int>

    @Query("SELECT * FROM District " +
            "ORDER BY ABS(latitude - :latitude) + ABS(longitude - :longitude) ASC " +
            "LIMIT 1")
    fun selectDistrict(latitude: Double, longitude: Double): Flow<District>

    @Query("SELECT * FROM District WHERE recent_search_yn == 'y'")
    fun selectRecentSearchDistricts(): Flow<List<District>>

    @Query("SELECT * FROM District WHERE district_name LIKE :keyword ORDER BY id DESC LIMIT :loadSize OFFSET :index * :loadSize")
    fun getDistrictPageNames(keyword: String, index: Int, loadSize: Int): List<District>

    @Update
    suspend fun updateDistrict(district: District)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDistrictList(districtList: List<District>)
}