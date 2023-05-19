package com.riopermana.database.di

import com.riopermana.database.CompanyListingDao
import com.riopermana.database.StockDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun providesCompanyListingsDao(
        database: StockDatabase
    ) : CompanyListingDao = database.companyListingDao

}