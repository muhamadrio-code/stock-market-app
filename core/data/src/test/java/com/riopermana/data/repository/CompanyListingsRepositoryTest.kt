package com.riopermana.data.repository

import com.riopermana.network.datasource.RemoteDataSource
import fake.FakeCompanyListingsDao
import fake.FakeStockRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompanyListingsRepositoryTest {

    private lateinit var repository: CompanyListingsRepository
    private lateinit var companyListingsDao: FakeCompanyListingsDao
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = FakeStockRemoteDataSource()
        companyListingsDao = FakeCompanyListingsDao()
        repository = CompanyListingsRepository(remoteDataSource, companyListingsDao)
    }


    @Test
    fun `test getAllCompanyListing return data`() = runTest {

    }
}