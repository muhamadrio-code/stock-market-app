package com.riopermana.data.repository.contract

import com.riopermana.common.Resource
import com.riopermana.data.Syncable
import com.riopermana.data.model.CompanyListings
import kotlinx.coroutines.flow.Flow

interface CompanyListingsRepository : Syncable {
    fun getCompanyListings(): Flow<List<CompanyListings>>
    fun getCompanyListings(query: String): Flow<Resource<List<CompanyListings>>>
    fun insertCompanyListings(list: List<CompanyListings>)
}