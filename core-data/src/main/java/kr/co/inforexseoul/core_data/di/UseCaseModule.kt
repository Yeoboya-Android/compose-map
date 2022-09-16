package kr.co.inforexseoul.core_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_data.repository.TestRepository
import kr.co.inforexseoul.core_data.usecase.GetTestDataUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    /**
     * TODO 테스트용
     * */
    @Provides
    @Singleton
    fun providesGetTestDataUseCase(
        testRepository: TestRepository
    ): GetTestDataUseCase = GetTestDataUseCase(testRepository)

}