package kr.co.inforexseoul.core_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_data.repository.MapRepository
import kr.co.inforexseoul.core_data.repository.OpenWeatherRepository
import kr.co.inforexseoul.core_data.repository.PapagoRepository
import kr.co.inforexseoul.core_data.repository.VillageForecastRepository
import kr.co.inforexseoul.core_data.usecase.GetBusStationDataUseCase
import kr.co.inforexseoul.core_data.usecase.GetOpenWeatherForecastUseCase
import kr.co.inforexseoul.core_data.usecase.GetTranslateTextUseCase
import kr.co.inforexseoul.core_data.usecase.GetVillageForecastUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesGetOpenWeatherForecastUseCase(
        openWeatherRepository: OpenWeatherRepository
    ): GetOpenWeatherForecastUseCase = GetOpenWeatherForecastUseCase(openWeatherRepository)

    @Provides
    @Singleton
    fun providesGetVillageForecastForecastUseCase(
        villageForecastRepository: VillageForecastRepository
    ): GetVillageForecastUseCase = GetVillageForecastUseCase(villageForecastRepository)

    @Provides
    @Singleton
    fun providesGetBusStationDataUseCase(
        mapRepository: MapRepository
    ) : GetBusStationDataUseCase = GetBusStationDataUseCase(mapRepository)

    @Provides
    @Singleton
    fun providesGetTranslateTextUseCase(
        papagoRepository: PapagoRepository
    ) : GetTranslateTextUseCase = GetTranslateTextUseCase(papagoRepository)
}