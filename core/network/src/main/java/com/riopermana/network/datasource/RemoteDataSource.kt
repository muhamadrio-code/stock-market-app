package com.riopermana.network.datasource

import com.riopermana.network.dto.CompanyListingDto

/**
* Base Interface for remote data source
*/
interface RemoteDataSource {
    suspend fun getCompanyListing() : List<CompanyListingDto>
}