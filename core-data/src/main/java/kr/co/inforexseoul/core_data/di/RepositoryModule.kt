package kr.co.inforexseoul.core_data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_data.repository.TestRepository
import kr.co.inforexseoul.core_data.repository.TestRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    /**
     * @TODO 테스트용
     * */
    @Binds
    fun bindsTestRepository(
        testRepository: TestRepositoryImpl
    ): TestRepository

}