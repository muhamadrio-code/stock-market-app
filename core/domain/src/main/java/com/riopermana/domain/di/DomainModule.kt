package com.riopermana.domain.di

import com.riopermana.domain.GetCompanyListingsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindsGetCompanyListingsUseCase(
        companyListingsUseCase: GetCompanyListingsUseCase
    ): GetCompanyListingsUseCase
}