package com.riopermana.data.repository

import com.riopermana.data.mapper.toEntity
import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.contract.ICompanyListingsRepository
import com.riopermana.database.CompanyListingDao
import com.riopermana.network.datasource.RemoteDataSource
import com.riopermana.network.dto.CompanyListingDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CompanyListingsRepository @Inject constructor (
    private val remoteDataSource: RemoteDataSource,
    private val companyListingsDao: CompanyListingDao
) : ICompanyListingsRepository {

    override fun getCompanyListings(): Flow<List<CompanyListings>> {

        return flow {
            runCatching {
                val response = remoteDataSource.getCompanyListing()
                companyListingsDao.insertCompanyListingsOrAbort(
                    response.map(CompanyListingDto::toEntity)
                )
            }
        }
    }

    override fun getCompanyListings(query: String): Flow<List<CompanyListings>> {
        throw Exception()
    }

    override fun insertCompanyListings(list: List<CompanyListings>) {
        throw Exception()
    }
}