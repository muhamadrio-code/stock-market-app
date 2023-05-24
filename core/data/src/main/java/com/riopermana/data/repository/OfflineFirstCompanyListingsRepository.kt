package com.riopermana.data.repository

import com.riopermana.common.Resource
import com.riopermana.data.Synchronizer
import com.riopermana.data.mapper.toEntity
import com.riopermana.data.mapper.toExternalModel
import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.contract.CompanyListingsRepository
import com.riopermana.data.syncData
import com.riopermana.database.CompanyListingDao
import com.riopermana.database.entities.CompanyListingEntity
import com.riopermana.network.datasource.RemoteDataSource
import com.riopermana.network.dto.CompanyListingDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstCompanyListingsRepository @Inject constructor (
    private val remoteDataSource: RemoteDataSource,
    private val companyListingsDao: CompanyListingDao
) : CompanyListingsRepository {

    override fun getCompanyListings(): Flow<List<CompanyListings>> {
        return companyListingsDao.getCompanyListings().map {
            it.mapNotNull(CompanyListingEntity::toExternalModel)
        }
    }

    override fun getCompanyListings(query: String): Flow<Resource<List<CompanyListings>>> {
        throw Exception()
    }

    override fun insertCompanyListings(list: List<CompanyListings>) {
        throw Exception()
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return synchronizer.syncData(
            fetcher = remoteDataSource::getCompanyListing,
            onFetchSuccess = {
                companyListingsDao.clearAndInsert(it.map(CompanyListingDto::toEntity))
            }
        )
    }
}