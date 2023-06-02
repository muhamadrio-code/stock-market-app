package com.riopermana.data.repository

import com.riopermana.data.Synchronizer
import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.fake.FakeCompanyListingsDao
import com.riopermana.database.CompanyListingDao
import com.riopermana.database.entities.CompanyListingEntity
import com.riopermana.network.datasource.RemoteDataSource
import com.riopermana.network.dto.CompanyListingDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompanyListingsRepositoryTest {

    private lateinit var mockRemoteDataSource: RemoteDataSource
    private lateinit var mockDao: CompanyListingDao
    private lateinit var repository: OfflineFirstCompanyListingsRepository
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        mockRemoteDataSource = mockk()
        mockDao = mockk()
        repository = OfflineFirstCompanyListingsRepository(mockRemoteDataSource, mockDao)
    }


    @Test
    fun `test getAllCompanyListings return data`() = testScope.runTest {
        val expected = listOf(
            CompanyListings(1, "A", "Agilent Technologies Inc", "NYSE"),
            CompanyListings(2, "AA", "Alcoa Corp", "NYSE"),
        )

        every { mockDao.getCompanyListings() } returns flowOf(
            listOf(
                CompanyListingEntity(1, "A", "Agilent Technologies Inc", "NYSE"),
                CompanyListingEntity(2, "AA", "Alcoa Corp", "NYSE"),
            )
        )

        val actual = repository.getCompanyListings().first()
        verify(exactly = 1) { mockDao.getCompanyListings() }
        assertEquals(expected, actual)

    }

    @Test
    fun `test getAllCompanyListings by query return data`() = testScope.runTest {
        val query = "aa"
        val expected = listOf(CompanyListings(2, "AA", "Alcoa Corp", "NYSE"))

        every { mockDao.getCompanyListings(query) } returns flowOf(
            listOf(
                CompanyListingEntity(
                    2,
                    "AA",
                    "Alcoa Corp",
                    "NYSE"
                )
            )
        )

        val actual = repository.getCompanyListings(query).first()
        verify(exactly = 1) { mockDao.getCompanyListings(query) }
        assertEquals(expected, actual)
    }


    @Test
    fun `test databaseSync, verify fetch data from internet, clear current data and insert new data to the database`() =
        testScope.runTest {
            val fakeCompanyListingsDao = FakeCompanyListingsDao()
            val repo =
                OfflineFirstCompanyListingsRepository(mockRemoteDataSource, fakeCompanyListingsDao)
            val noopSynchronizer = object : Synchronizer {}
            val remoteData = listOf(
                CompanyListingDto("AZA", "Foo tch", "NYE"),
                CompanyListingDto("AKA", "Thursday Corp", "NYSE"),
            )

            val expected = listOf(
                CompanyListingEntity(0, "AZA", "Foo tch", "NYE"),
                CompanyListingEntity(0, "AKA", "Thursday Corp", "NYSE"),
            )

            coEvery { mockRemoteDataSource.getCompanyListing() } returns remoteData

            repo.syncWith(noopSynchronizer)
            coVerify { mockRemoteDataSource.getCompanyListing() }

            val actual = fakeCompanyListingsDao.getCompanyListings().first()

            assertEquals(expected, actual)
        }

}