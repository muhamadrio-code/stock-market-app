package com.riopermana.testing.util

import com.riopermana.data.util.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestNetworkMonitor : NetworkMonitor {

    private val _isOnline = MutableStateFlow(true)
    override val isOnline: Flow<Boolean>
        get() = _isOnline

    fun setConnected(isConnected: Boolean) {
        _isOnline.value = isConnected
    }

}