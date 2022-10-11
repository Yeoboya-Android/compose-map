package kr.co.inforexseoul.core_database.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_database.dao.DistrictDao
import kr.co.inforexseoul.core_database.database.AppDatabase
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "compose_map.db")
            .setQueryCallback(
                { sqlQuery, _ -> /*Log.i("qwe123", "sqlQuery: $sqlQuery")*/ },
                Executors.newSingleThreadExecutor()
            )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDistrictDao(appDatabase: AppDatabase): DistrictDao = appDatabase.getDistrictDao()
}