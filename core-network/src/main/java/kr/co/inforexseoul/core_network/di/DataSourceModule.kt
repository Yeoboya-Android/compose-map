package kr.co.inforexseoul.core_network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_network.datasource.TestRemoteDataSource
import kr.co.inforexseoul.core_network.datasource.TestRemoteDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    /**
     * TODO 테스트용
     * */
    @Binds
    fun bindsTestRemoteDataSource(
        testRemoteDataSource: TestRemoteDataSourceImpl
    ): TestRemoteDataSource
}