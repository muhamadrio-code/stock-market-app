package com.riopermana.sync.worker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.riopermana.data.repository.contract.CompanyListingsRepository
import com.riopermana.sync.test.TestSyncWorkerFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SyncWorkerImplementationTest {

    private lateinit var repo: CompanyListingsRepository
    private lateinit var context: Context
    private lateinit var worker: SyncWorker

    @Before
    fun setUp() {
        repo = spyk()
        context = ApplicationProvider.getApplicationContext()
        worker = TestListenableWorkerBuilder<SyncWorker>(context)
            .setWorkerFactory(TestSyncWorkerFactory(repo, UnconfinedTestDispatcher()))
            .build()
    }

    @Test
    fun test_SyncWorker_return_Result_success() = runTest {
        coEvery { repo.syncWith(worker) } returns true

        val result = worker.doWork()
        Assert.assertEquals(ListenableWorker.Result.success(), result)

        coVerify(exactly = 1) { repo.syncWith(worker) }
    }

    @Test
    fun test_SyncWorker_return_Result_retry() = runTest {
        coEvery { repo.syncWith(worker) } returns false

        val result = worker.doWork()
        Assert.assertEquals(ListenableWorker.Result.retry(), result)

        coVerify(exactly = 1) { repo.syncWith(worker) }
    }
}