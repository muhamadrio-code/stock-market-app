package com.riopermana.sync.worker

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import com.riopermana.data.repository.contract.CompanyListingsRepository
import com.riopermana.sync.test.TestSyncWorkerFactory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class SyncWorkerTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var repo: CompanyListingsRepository
    private lateinit var worker: SyncWorker

    @Before
    fun setUp() {
        repo = spyk()
        worker = TestListenableWorkerBuilder<SyncWorker>(context)
            .setWorkerFactory(TestSyncWorkerFactory(repo, UnconfinedTestDispatcher()))
            .build()
    }

    @Test
    fun test_SyncWorker_constraint() {
        val config = Configuration.Builder()
            .setExecutor(SynchronousExecutor())
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

        // Create request
        val request = SyncWorker.createOneTimeWorkRequest()

        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)!!

        // Enqueue and wait for result.
        workManager.enqueue(request).result.get()

        // Get WorkInfo and outputData
        val preRunWorkInfo = workManager.getWorkInfoById(request.id).get()

        // Assert
        assertEquals(WorkInfo.State.ENQUEUED, preRunWorkInfo.state)

        // Tells the testing framework that the constraints have been met
        testDriver.setAllConstraintsMet(request.id)

        val postRequirementWorkInfo = workManager.getWorkInfoById(request.id).get()
        assertEquals(WorkInfo.State.RUNNING, postRequirementWorkInfo.state)
    }

    @Test
    fun test_SyncWorker_return_Result_success() = runTest {
        coEvery { repo.syncWith(worker) } returns true

        val result = worker.doWork()
        assertEquals(ListenableWorker.Result.success(), result)

        coVerify(exactly = 1) { repo.syncWith(worker) }
    }

    @Test
    fun test_SyncWorker_return_Result_failure() = runTest {
        coEvery { repo.syncWith(worker) } returns false

        val result = worker.doWork()
        assertEquals(ListenableWorker.Result.failure(), result)

        coVerify(exactly = 1) { repo.syncWith(worker) }
    }
}