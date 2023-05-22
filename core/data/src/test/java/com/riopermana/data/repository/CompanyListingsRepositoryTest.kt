package com.riopermana.data.repository

import com.riopermana.database.CompanyListingDao
import com.riopermana.network.datasource.RemoteDataSource
import fake.FakeCompanyListingsDao
import fake.FakeStockRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompanyListingsRepositoryTest {

    private lateinit var repository: CompanyListingsRepository
    private lateinit var companyListingsDao: FakeCompanyListingsDao
    private lateinit var remoteDataSource: RemoteDataSource
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        remoteDataSource = FakeStockRemoteDataSource()
        companyListingsDao = FakeCompanyListingsDao()
        repository = CompanyListingsRepository(remoteDataSource, companyListingsDao)
    }


    @Test
    fun `test getAllCompanyListing return data`() = testScope.runTest {
        val mock = mockk<RemoteDataSource>()
        val dao = mockk<CompanyListingDao>()
        val repo = CompanyListingsRepository(mock, dao)

        coEvery { mock.getCompanyListing() } returns emptyList()

        repo.getCompanyListings()

        coVerify { mock.getCompanyListing() }

    }
}