package com.riopermana.data

import com.riopermana.common.suspendRunCatching

interface Synchronizer {

    /**
     * Syntactic sugar to call [Syncable.syncWith].
     */
    suspend fun Syncable.sync() : Boolean = this.syncWith(this@Synchronizer)
}

/**
 * Interface marker for a class that is synchronized with a remote source. Syncing must not be
 * performed concurrently and it is the [Synchronizer]'s responsibility to ensure this.
 */
interface Syncable {

    /**
     * Synchronizes the local database backing the repository with the network.
     * Returns if the sync was successful or not.
     */
    suspend fun syncWith(synchronizer: Synchronizer) : Boolean
}

suspend inline fun <reified T>Synchronizer.syncData(
    crossinline fetcher: suspend () -> T,
    crossinline onFetchSuccess: suspend (T) -> Unit
) : Boolean = suspendRunCatching {
    onFetchSuccess(fetcher())
}.isSuccess