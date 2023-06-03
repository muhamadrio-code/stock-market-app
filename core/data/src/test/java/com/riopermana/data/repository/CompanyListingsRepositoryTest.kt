package com.riopermana.data.repository

import com.google.common.truth.Truth.*
import com.riopermana.data.Synchronizer
import com.riopermana.data.mapper.toListExternalModel
import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.fake.FakeCompanyListingsDao
import com.riopermana.database.entities.CompanyListingEntity
import com.riopermana.network.datasource.RemoteDataSource
import com.riopermana.network.dto.CompanyListingDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompanyListingsRepositoryTest {

    private lateinit var mockRemoteDataSource: RemoteDataSource
    private lateinit var companyListingsDao: FakeCompanyListingsDao
    private lateinit var spyDao: FakeCompanyListingsDao
    private lateinit var repository: OfflineFirstCompanyListingsRepository
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        mockRemoteDataSource = mockk()
        companyListingsDao = FakeCompanyListingsDao()
        spyDao = spyk(companyListingsDao)
        repository = OfflineFirstCompanyListingsRepository(mockRemoteDataSource, spyDao)
    }


    @Test
    fun `test getAllCompanyListings return data`() = testScope.runTest {
        companyListingsDao.insertOrIgnoreCompanyListings(testEntityData)

        val actual = repository.getCompanyListings().first()

        verify(exactly = 1) { spyDao.getCompanyListings() }
        assertEquals(testEntityData.toListExternalModel(), actual)
    }

    @Test
    fun `test getAllCompanyListings by query return data`() = testScope.runTest {
        companyListingsDao.insertOrIgnoreCompanyListings(testEntityData)

        val query = "Agilent"
        val expected = listOf(CompanyListings("A", "Agilent Technologies Inc", "NYSE"))

        val actual = repository.getCompanyListings(query).first()
        verify(exactly = 1) { spyDao.getCompanyListings(query) }
        assertEquals(expected, actual)
    }


    @Test
    fun `test syncWith, verify fetch data from internet and insert new data to the database`() =
        testScope.runTest {
            val noopSynchronizer = object : Synchronizer {}
            val testData = listOf(
                CompanyListingDto("AZA", "Foo tch", "NYE"),
                CompanyListingDto("AKA", "Thursday Corp", "NYSE"),
            )

            coEvery { mockRemoteDataSource.getCompanyListing() } returns testData

            repository.syncWith(noopSynchronizer)
            coVerify(exactly = 1) { mockRemoteDataSource.getCompanyListing() }

            val actual = companyListingsDao.getCompanyListings().first()
            val expected = listOf(
                CompanyListingEntity("AZA", "Foo tch", "NYE"),
                CompanyListingEntity("AKA", "Thursday Corp", "NYSE"),
            )

            assertEquals(expected, actual)
        }

}

private val testEntityData = listOf(
    CompanyListingEntity("A", "Agilent Technologies Inc", "NYSE"),
    CompanyListingEntity("AA", "Alcoa Corp", "NYSE"),
    CompanyListingEntity("AAA", "AAX FIRST PRIORITY CLO BOND ETF", "NYSE ARCA"),
    CompanyListingEntity("AEAE", "AltEnergy Acquisition Corp - Class A", "NASDAQ"),
    CompanyListingEntity("ADXN", "Addex Therapeutics Ltd", "NASDAQ"),
    CompanyListingEntity("AA", "Aether Ltd", "NASDAQ"),
)