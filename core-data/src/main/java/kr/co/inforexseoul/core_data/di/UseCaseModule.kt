package kr.co.inforexseoul.core_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_data.repository.MapRepository
import kr.co.inforexseoul.core_data.repository.OpenWeatherMapRepository
import kr.co.inforexseoul.core_data.usecase.GetBusStationDataUseCase
import kr.co.inforexseoul.core_data.usecase.GetOpenWeatherMapDataUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesGetOpenWeatherMapDataUseCase(
        openWeatherMapRepository: OpenWeatherMapRepository
    ): GetOpenWeatherMapDataUseCase = GetOpenWeatherMapDataUseCase(openWeatherMapRepository)

    @Provides
    @Singleton
    fun providesGetBusStationDataUseCase(
        mapRepository: MapRepository
    ) : GetBusStationDataUseCase = GetBusStationDataUseCase(mapRepository)
}