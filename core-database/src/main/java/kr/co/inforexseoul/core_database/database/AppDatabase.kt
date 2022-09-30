package kr.co.inforexseoul.core_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.co.inforexseoul.core_database.dao.DistrictDao
import kr.co.inforexseoul.core_database.entity.District

@Database(entities = [District::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDistrictDao(): DistrictDao
}