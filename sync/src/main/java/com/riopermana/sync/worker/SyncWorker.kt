package com.riopermana.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.tracing.traceAsync
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.riopermana.data.Synchronizer
import com.riopermana.sync.helper.foregroundInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor (
    @Assisted private val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val dispatcher: CoroutineDispatcher
) : CoroutineWorker(appContext, workerParameters), Synchronizer {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.foregroundInfo()

    override suspend fun doWork(): Result = withContext(dispatcher) {
        traceAsync("sync", 0) {
            val syncedSuccessfully = true

            if (syncedSuccessfully) {
                Result.success()
            } else {
                Result.retry()
            }
        }
    }

    companion object {

        private val syncConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        /**
         * Expedited one time work to sync data on app startup
         */
        fun createOneTimeWorkRequest() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setConstraints(syncConstraints)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(DelegatingWorker::class.delegateData())
            .build()
    }
}