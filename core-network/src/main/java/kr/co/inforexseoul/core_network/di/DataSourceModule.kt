package kr.co.inforexseoul.core_network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_network.datasource.MapRemoteDataSource
import kr.co.inforexseoul.core_network.datasource.MapRemoteDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    /**
     * 맵 관련 API
     */
    @Binds
    fun bindsMapRemoteDataResource(
        mapRemoteDataSource: MapRemoteDataSourceImpl
    ) : MapRemoteDataSource
}