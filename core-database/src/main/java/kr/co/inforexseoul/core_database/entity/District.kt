package kr.co.inforexseoul.core_database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class District(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "district_id") val districtId: Long,
    @ColumnInfo(name = "district_name") val districtName: String,
    @ColumnInfo(name = "nx") val nx: Int,
    @ColumnInfo(name = "ny") val ny: Int,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "recent_search_yn") val recentSearchYn: String = "n",
)