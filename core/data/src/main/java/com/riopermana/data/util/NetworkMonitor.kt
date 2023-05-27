package com.riopermana.data.util

import kotlinx.coroutines.flow.Flow


/**
 * Utilities for monitoring network connectivity
 */
interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}