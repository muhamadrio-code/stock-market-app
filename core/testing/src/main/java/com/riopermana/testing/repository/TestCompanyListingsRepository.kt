package com.riopermana.testing.repository

import com.riopermana.data.Synchronizer
import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.contract.CompanyListingsRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestCompanyListingsRepository : CompanyListingsRepository {

    /**
     * The backing hot flow for the list of companyListings for testing.
     */
    private val companyListingsFlow = MutableSharedFlow<List<CompanyListings>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    /**
     * A test-only API to allow controlling the list of companyListings from tests.
     */
    fun sendCompanyListings(companyListings: List<CompanyListings>) {
        companyListingsFlow.tryEmit(companyListings)
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return true
    }

    override fun getCompanyListings(): Flow<List<CompanyListings>> {
        return companyListingsFlow
    }

    override fun getCompanyListings(query: String): Flow<List<CompanyListings>> {
        return companyListingsFlow.map { companyListingsList ->
            companyListingsList.filter { companyListings ->
                companyListings.name.lowercase().contains(
                    query.lowercase().toRegex()
                ) || companyListings.symbol == query.uppercase()
            }
        }
    }
}