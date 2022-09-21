package kr.co.inforexseoul.core_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_data.repository.MapRepository
import kr.co.inforexseoul.core_data.usecase.GetBusStationDataUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesGetBusStationDataUseCase(
        mapRepository: MapRepository
    ) : GetBusStationDataUseCase = GetBusStationDataUseCase(mapRepository)
}