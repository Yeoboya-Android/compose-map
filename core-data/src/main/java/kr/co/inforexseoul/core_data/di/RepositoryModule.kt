package kr.co.inforexseoul.core_data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_data.repository.TestRepository
import kr.co.inforexseoul.core_network.datasource.TestRemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsTestRepository(
        testRemoteDataSource: TestRemoteDataSource
    ): TestRepository

}