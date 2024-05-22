package com.spartabasic.www.domain.di

import com.spartabasic.www.domain.repository.CatRepository
import com.spartabasic.www.domain.usecase.FetchCatsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideFetchCatsUseCase(
        catsRepository: CatRepository
    ) = FetchCatsUseCase(catsRepository = catsRepository)
}