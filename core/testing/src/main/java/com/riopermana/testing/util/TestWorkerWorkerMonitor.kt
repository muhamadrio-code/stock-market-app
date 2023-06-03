package com.riopermana.testing.util

import com.riopermana.sync.status.SyncWorkerMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestWorkerWorkerMonitor : SyncWorkerMonitor {
    private val _isSyncing = MutableStateFlow(true)
    override val isResultSuccess: Flow<Boolean>
        get() = _isSyncing

    fun setSyncing(isSyncing: Boolean) {
        _isSyncing.value = isSyncing
    }
}