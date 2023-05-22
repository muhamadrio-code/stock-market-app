package com.riopermana.sync.di

import com.riopermana.sync.status.SyncStatusMonitor
import com.riopermana.sync.status.WorkManagerSyncStatusMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface SyncModule {
    @Binds
    fun bindsSyncStatusMonitor(monitor: WorkManagerSyncStatusMonitor) : SyncStatusMonitor
}