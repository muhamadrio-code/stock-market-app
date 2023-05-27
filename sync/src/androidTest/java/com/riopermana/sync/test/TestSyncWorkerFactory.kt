package com.riopermana.sync.test

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.riopermana.data.repository.contract.CompanyListingsRepository
import com.riopermana.sync.worker.SyncWorker
import kotlinx.coroutines.CoroutineDispatcher

class TestSyncWorkerFactory(
    private val companyListingsRepository: CompanyListingsRepository,
    private val dispatcher: CoroutineDispatcher,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker {
        return SyncWorker(appContext, workerParameters, companyListingsRepository, dispatcher)
    }
}