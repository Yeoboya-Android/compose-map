package kr.co.inforexseoul.core_database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class District(
    @PrimaryKey val districtId: Long,
    @ColumnInfo(name = "district_name") val districtName: String,
    @ColumnInfo(name = "nx") val nx: Int,
    @ColumnInfo(name = "ny") val ny: Int,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
)