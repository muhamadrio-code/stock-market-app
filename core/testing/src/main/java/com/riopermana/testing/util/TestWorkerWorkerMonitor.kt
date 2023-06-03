package com.riopermana.testing.util

import com.riopermana.sync.status.SyncWorkerMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestWorkerWorkerMonitor : SyncWorkerMonitor {
    private val _isResultSuccess = MutableStateFlow(true)
    override val isResultSuccess: Flow<Boolean>
        get() = _isResultSuccess

    fun setResult(isSuccess: Boolean) {
        _isResultSuccess.value = isSuccess
    }
}