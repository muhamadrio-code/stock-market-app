package com.riopermana.data.repository

import com.riopermana.data.Synchronizer
import com.riopermana.data.databaseSync
import com.riopermana.data.mapper.toEntity
import com.riopermana.data.mapper.toListExternalModel
import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.contract.CompanyListingsRepository
import com.riopermana.database.CompanyListingDao
import com.riopermana.database.entities.CompanyListingEntity
import com.riopermana.network.datasource.RemoteDataSource
import com.riopermana.network.dto.CompanyListingDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * Database backed implementation of the [CompanyListingsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstCompanyListingsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val companyListingsDao: CompanyListingDao,
) : CompanyListingsRepository {

    override fun getCompanyListings(): Flow<List<CompanyListings>> {
        return companyListingsDao.getCompanyListings()
            .map(List<CompanyListingEntity>::toListExternalModel)
    }

    override fun getCompanyListings(query: String): Flow<List<CompanyListings>> {
        return companyListingsDao.getCompanyListings(query)
            .map(List<CompanyListingEntity>::toListExternalModel)
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return synchronizer.databaseSync(
            fetcher = remoteDataSource::getCompanyListing,
            databaseUpdater = companyListingsDao::insertOrIgnoreCompanyListings,
            mapper = CompanyListingDto::toEntity
        )
    }
}