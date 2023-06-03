package com.riopermana.domain.di

import com.riopermana.common.helper.ResourcesHelper
import com.riopermana.data.repository.contract.CompanyListingsRepository
import com.riopermana.domain.GetCompanyListingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun bindsGetCompanyListingsUseCase(
        companyListingsRepository: CompanyListingsRepository,
        resourcesHelper: ResourcesHelper<String>
    ): GetCompanyListingsUseCase =
        GetCompanyListingsUseCase(companyListingsRepository, resourcesHelper)
}