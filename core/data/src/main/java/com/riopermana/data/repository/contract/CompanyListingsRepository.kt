package com.riopermana.data.repository.contract

import com.riopermana.data.Syncable
import com.riopermana.data.model.CompanyListings
import kotlinx.coroutines.flow.Flow

interface CompanyListingsRepository : Syncable {
    /**
     * Get all available company listing as a stream.
     */
    fun getCompanyListings(): Flow<List<CompanyListings>>

    /**
     * Get company listing matches the query.
     */
    fun getCompanyListings(query: String): Flow<List<CompanyListings>>
}