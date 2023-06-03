package com.riopermana.data.repository.fake

import com.riopermana.database.CompanyListingDao
import com.riopermana.database.entities.CompanyListingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCompanyListingsDao : CompanyListingDao {
    private val companyListings = mutableSetOf<CompanyListingEntity>()

    override suspend fun insertOrIgnoreCompanyListings(list: List<CompanyListingEntity>) {
        companyListings.addAll(list)
    }

    override fun getCompanyListings(): Flow<List<CompanyListingEntity>> {
        return flowOf(companyListings.toList())
    }

    override fun getCompanyListings(query: String): Flow<List<CompanyListingEntity>> {
        return flowOf(
            companyListings.filter { entity ->
                entity.name.lowercase().contains(
                    query.lowercase().toRegex()
                ) || entity.symbol == query.uppercase()
            }
        )
    }
}