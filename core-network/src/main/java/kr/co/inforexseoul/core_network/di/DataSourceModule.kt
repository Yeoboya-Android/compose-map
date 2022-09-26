package kr.co.inforexseoul.core_network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_network.datasource.*

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    /**
     * 날씨 관련 API
     */
    @Binds
    fun bindsOpenWeatherRemoteDataSource(
        openWeatherRemoteDataSourceImpl: OpenWeatherRemoteDataSourceImpl
    ): OpenWeatherRemoteDataSource

    @Binds
    fun bindsVillageForecastRemoteDataSource(
        villageForecastRemoteDataSourceImpl: VillageForecastRemoteDataSourceImpl
    ): VillageForecastRemoteDataSource

    /**
     * 맵 관련 API
     */
    @Binds
    fun bindsMapRemoteDataResource(
        mapRemoteDataSource: MapRemoteDataSourceImpl
    ) : MapRemoteDataSource
}