package kr.co.inforexseoul.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.co.inforexseoul.core_database.entity.District

@Dao
interface DistrictDao {

    @Query("SELECT COUNT(*) FROM District")
    fun selectDistrictCount(): Flow<Int>

    @Query("SELECT * FROM District " +
            "WHERE latitude<>:latitude " +
            "AND longitude<>:longitude " +
            "ORDER BY ABS(latitude - :latitude), ABS(longitude - :longitude) ASC " +
            "LIMIT 1")
    fun selectDistrict(latitude: Double, longitude: Double): Flow<District>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDistrictList(districtList: List<District>)
}