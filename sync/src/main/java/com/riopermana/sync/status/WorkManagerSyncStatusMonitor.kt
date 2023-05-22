package com.riopermana.sync.status

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.riopermana.sync.initializer.SyncWorkName
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Reports on if synchronization is in progress
 */
interface SyncStatusMonitor {
    val isSyncing: Flow<Boolean>
}


/**
 * [SyncStatusMonitor] backed by [WorkInfo] from [WorkManager]
 */
class WorkManagerSyncStatusMonitor @Inject constructor(
    @ApplicationContext context: Context
) : SyncStatusMonitor {

    override val isSyncing = WorkManager.getInstance(context)
        .getWorkInfosForUniqueWorkLiveData(SyncWorkName)
        .map(MutableList<WorkInfo>::anyRunning)
        .asFlow()

}


private val List<WorkInfo>.anyRunning get() = any { it.state == WorkInfo.State.RUNNING }