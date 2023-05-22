package com.riopermana.data.repository

import com.riopermana.common.Resource
import com.riopermana.data.Synchronizer
import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.contract.ICompanyListingsRepository
import com.riopermana.database.CompanyListingDao
import com.riopermana.network.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompanyListingsRepository @Inject constructor (
    private val remoteDataSource: RemoteDataSource,
    private val companyListingsDao: CompanyListingDao
) : ICompanyListingsRepository {

    override suspend fun getCompanyListings(): Flow<Resource<List<CompanyListings>>> {

        return flow {
            remoteDataSource.getCompanyListing()
            emit(Resource.Success(emptyList()))
        }
    }

    override fun getCompanyListings(query: String): Flow<Resource<List<CompanyListings>>> {
        throw Exception()
    }

    override fun insertCompanyListings(list: List<CompanyListings>) {
        throw Exception()
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        TODO("Not yet implemented")
    }
}