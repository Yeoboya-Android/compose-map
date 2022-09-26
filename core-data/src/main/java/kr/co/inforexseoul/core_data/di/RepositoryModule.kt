package kr.co.inforexseoul.core_data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_data.repository.*

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsOpenWeatherMapRepository(
        weatherRepository: OpenWeatherMapRepositoryImpl
    ): OpenWeatherMapRepository

    @Binds
    fun bindsMapRepository(
        mapRepository: MapRepositoryImpl
    ) : MapRepository

    @Binds
    fun bindsDistrictRepository(
        districtRepository: DistrictRepositoryImpl
    ) : DistrictRepository
}