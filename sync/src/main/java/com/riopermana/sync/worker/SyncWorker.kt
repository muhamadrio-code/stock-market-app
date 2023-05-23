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
import com.riopermana.data.repository.contract.ICompanyListingsRepository
import com.riopermana.sync.helper.foregroundInfo
import dagger.assisted.Assisted
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker(
    @Assisted val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val companyListingsRepository: ICompanyListingsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineWorker(appContext, workerParameters), Synchronizer {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.foregroundInfo()

    override suspend fun doWork(): Result = withContext(dispatcher) {
        traceAsync("sync", 0) {
            val syncedSuccessfully =
                withContext(Dispatchers.Default) { companyListingsRepository.sync() }

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