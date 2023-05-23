package com.riopermana.data.di

import com.riopermana.data.repository.CompanyListingsRepository
import com.riopermana.data.repository.contract.ICompanyListingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsCompanyListingRepository(
        companyListingsRepository: CompanyListingsRepository
    ) : ICompanyListingsRepository
}