package com.riopermana.sync.initializer

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import androidx.work.WorkManagerInitializer
import com.riopermana.sync.worker.SyncWorker

object Sync {

    /**
     * This method is a workaround to manually initialize the sync process instead of relying on
     * automatic initialization with Androidx Startup. It is called from the app module's
     * Application.onCreate() and should be only done once.
     */
    fun initializeSync(context: Context) {
        AppInitializer.getInstance(context)
            .initializeComponent(SyncInitializer::class.java)
    }
}

// This name should not be changed otherwise the app may have concurrent sync requests running
internal const val SyncWorkName = "stockmarket.SyncWorker"


/**
 * Registers work to sync the data layer periodically on app startup.
 */
class SyncInitializer : Initializer<Sync> {

    override fun create(context: Context): Sync {
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                SyncWorkName,
                ExistingWorkPolicy.KEEP,
                SyncWorker.createOneTimeWorkRequest()
            )
        }

        return Sync
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(WorkManagerInitializer::class.java)

}