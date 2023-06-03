package com.riopermana.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.riopermana.database.entities.CompanyListingEntity
import kotlinx.coroutines.flow.Flow


/**
 * Data Access Object (DAO) for performing database operations related to company listings.
 * The CompanyListingDao interface defines the methods for accessing and manipulating company listing data
 * in the local database.
 */
@Dao
interface CompanyListingDao {

    /**
     * Inserts a list of company listings into the database or aborts (roll back) the transaction on conflict.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCompanyListings(list: List<CompanyListingEntity>)

    /**
     * Retrieves all of company listings from the local database.
     *
     * @return A [Flow] emitting a list of [CompanyListingEntity] objects.
     */
    @Query("SELECT * FROM company_listings")
    fun getCompanyListings(): Flow<List<CompanyListingEntity>>

    /**
     * Retrieves a flow of company listings based on the provided query from the local database.
     *
     * @return A [Flow] emitting a list of [CompanyListingEntity] objects that match the query.
     */
    @Query(
        """
            SELECT * FROM company_listings
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR 
            UPPER(symbol) == UPPER(:query)
        """
    )
    fun getCompanyListings(query: String): Flow<List<CompanyListingEntity>>
}