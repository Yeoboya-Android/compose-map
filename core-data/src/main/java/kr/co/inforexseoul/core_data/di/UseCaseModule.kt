package kr.co.inforexseoul.core_data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.inforexseoul.core_data.repository.TestRepository
import kr.co.inforexseoul.core_data.usecase.GetTestDataUseCase

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindsGetTestDataUseCase(
        testRepository: TestRepository
    ): GetTestDataUseCase

}