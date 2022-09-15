package kr.co.inforexseoul.core_network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_network.datasource.TestRemoteDataSource
import kr.co.inforexseoul.core_network.service.TestApiService

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsTestRemoteDataSource(
        testApiService: TestApiService
    ): TestRemoteDataSource
}