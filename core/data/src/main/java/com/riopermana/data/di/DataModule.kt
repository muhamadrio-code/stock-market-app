package com.riopermana.data.di

import com.riopermana.data.repository.OfflineFirstCompanyListingsRepository
import com.riopermana.data.repository.contract.CompanyListingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsCompanyListingRepository(
        companyListingsRepository: OfflineFirstCompanyListingsRepository
    ) : CompanyListingsRepository
}