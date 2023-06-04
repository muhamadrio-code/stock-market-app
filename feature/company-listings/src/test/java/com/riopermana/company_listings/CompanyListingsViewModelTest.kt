package com.riopermana.company_listings

import com.riopermana.common.doWhen
import com.riopermana.common.helper.ResourcesHelper
import com.riopermana.data.model.CompanyListings
import com.riopermana.domain.GetCompanyListingsUseCase
import com.riopermana.testing.data.companyListingsTestData
import com.riopermana.testing.repository.TestCompanyListingsRepository
import com.riopermana.testing.util.MainDispatcherRule
import com.riopermana.testing.util.TestStringResourcesHelper
import com.riopermana.testing.util.TestWorkerWorkerMonitor
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompanyListingsViewModelTest {

    @get:Rule
    val testDispatcherRule = MainDispatcherRule()

    private lateinit var companyListingsRepository: TestCompanyListingsRepository
    private lateinit var workerStatusMonitor: TestWorkerWorkerMonitor
    private lateinit var companyListingsUseCase: GetCompanyListingsUseCase
    private lateinit var resourcesHelper: ResourcesHelper<String>
    private lateinit var sut: CompanyListingsViewModel

    @Before
    fun setUp() {
        companyListingsRepository = TestCompanyListingsRepository()
        workerStatusMonitor = TestWorkerWorkerMonitor()
        resourcesHelper = TestStringResourcesHelper()
        companyListingsUseCase =
            GetCompanyListingsUseCase(companyListingsRepository, resourcesHelper)
        sut = CompanyListingsViewModel(
            companyListingsUseCase,
        )
    }

    @Test
    fun `test stream of query return data`() = runTest {
        // attach collector
        val job = launch(UnconfinedTestDispatcher()) {
            sut.query.collect()
        }

        // initial value
        val initialValue = sut.query.value
        assertNull(initialValue)

        // start query
        sut.getCompanyListingsByQuery("agi")

        // end value
        val actual = sut.query.value

        // assert
        assertEquals("agi", actual)

        // clean job
        job.cancel()
    }


    @Test
    fun `test get company listings on syncing`() = runTest {
        // attach collector
        val job = launch(UnconfinedTestDispatcher()) {
            sut.resourceState.collect()
        }
        // skip any delay
        advanceUntilIdle()

        // initial value on pre-sync
        val preSyncing = sut.resourceState.value
        preSyncing.doWhen(success = {
            assertEquals(emptyList<CompanyListings>(), it)
        })

        // on sync success and data is available
        companyListingsRepository.sendCompanyListings(companyListingsTestData)

        // skip any delay
        advanceUntilIdle()

        // value after sync
        val postSyncing = sut.resourceState.value
        postSyncing.doWhen(success = {
            assertEquals(companyListingsTestData, it)
        })

        // clean job
        job.cancel()
    }

    @Test
    fun `test get company listings on post syncing`() = runTest {
        // success sync and data is available
        companyListingsRepository.sendCompanyListings(companyListingsTestData)

        // attach collector
        val job = launch(UnconfinedTestDispatcher()) {
            sut.resourceState.collect()
        }

        // skip any delay
        advanceUntilIdle()

        // value after sync
        val actual = sut.resourceState.value
        actual.doWhen(success = {
            assertEquals(companyListingsTestData, it)
        })

        // clean job
        job.cancel()
    }

    @Test
    fun `test get company listings with query return data`() = runTest {
        // attach collector
        val job = launch(UnconfinedTestDispatcher()) {
            sut.resourceState.collect()
        }

        // skip any delay
        advanceUntilIdle()
        // success sync and data is available
        companyListingsRepository.sendCompanyListings(companyListingsTestData)

        // simulate user typing behaviour
        sut.getCompanyListingsByQuery("a")
        // assert that data is not change when user is typing
        sut.resourceState.value.doWhen(success = {
            assertEquals(companyListingsTestData, it)
        })

        // user continue typing
        sut.getCompanyListingsByQuery("ar")
        // user stop typing, then returns the result after 200ms
        advanceTimeBy(201)
        // assert result
        val actual = sut.resourceState.value
        actual.doWhen(success = {
            assertEquals(
                listOf(
                    CompanyListings("AAAU", "Arixt", "BATS"),
                    CompanyListings("AAC", "Ares", "NYSE"),
                ), it
            )
        })

        // clean job
        job.cancel()
    }

}