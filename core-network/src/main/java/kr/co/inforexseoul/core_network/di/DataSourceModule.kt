package kr.co.inforexseoul.core_network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_network.datasource.MapRemoteDataSource
import kr.co.inforexseoul.core_network.datasource.MapRemoteDataSourceImpl
import kr.co.inforexseoul.core_network.datasource.OpenWeatherMapRemoteDataSource
import kr.co.inforexseoul.core_network.datasource.OpenWeatherMapRemoteDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    /**
     * 날씨 관련 API
     */
    @Binds
    fun bindsWeatherRemoteDataSource(
        openWeatherMapRemoteDataSource: OpenWeatherMapRemoteDataSourceImpl
    ): OpenWeatherMapRemoteDataSource

    /**
     * 맵 관련 API
     */
    @Binds
    fun bindsMapRemoteDataResource(
        mapRemoteDataSource: MapRemoteDataSourceImpl
    ) : MapRemoteDataSource
}