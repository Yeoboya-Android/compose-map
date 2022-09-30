package kr.co.inforexseoul.core_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_data.pagingsource.DistrictPagingSource
import kr.co.inforexseoul.core_data.usecase.GetPageDistrictUseCase

@Module
@InstallIn(SingletonComponent::class)
object PagingSourceModule {

    @Provides
    fun providesDistrictPagingSource(
        keyword: String,
        getPageDistrictUseCase: GetPageDistrictUseCase
    ): DistrictPagingSource = DistrictPagingSource(keyword, getPageDistrictUseCase)

}