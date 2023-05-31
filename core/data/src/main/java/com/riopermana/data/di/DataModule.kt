package com.riopermana.data.di

import com.riopermana.data.repository.OfflineFirstCompanyListingsRepository
import com.riopermana.data.repository.contract.CompanyListingsRepository
import com.riopermana.data.util.NetworkConnectivityMonitor
import com.riopermana.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindsNetworkMonitor(
        networkMonitor: NetworkConnectivityMonitor
    ) : NetworkMonitor

    @Binds
    @Singleton
    fun bindsCompanyListingsRepository(
        companyListingsRepository: OfflineFirstCompanyListingsRepository
    ) : CompanyListingsRepository
}