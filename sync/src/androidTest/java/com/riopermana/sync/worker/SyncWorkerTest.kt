package com.riopermana.sync.worker

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SyncWorkerTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        val config = Configuration.Builder()
            .setExecutor(SynchronousExecutor())
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun testSimple() {
        val worker = SyncWorker.createOneTimeWorkRequest()
        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)

        workManager.enqueue(worker).result.get()

        val preWorkInfo = workManager.getWorkInfoById(worker.id).get()

        assertEquals(WorkInfo.State.ENQUEUED, preWorkInfo.state)
    }
}