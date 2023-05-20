package com.riopermana.data.repository.contract

import com.riopermana.data.model.CompanyListings
import kotlinx.coroutines.flow.Flow

interface ICompanyListingsRepository {
    fun getCompanyListings() : Flow<List<CompanyListings>>
    fun getCompanyListings(query: String) :  Flow<List<CompanyListings>>
    fun insertCompanyListings(list: List<CompanyListings>)
}