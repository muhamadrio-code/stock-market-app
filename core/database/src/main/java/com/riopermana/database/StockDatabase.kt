package com.riopermana.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riopermana.database.entities.CompanyListingEntity

@Database(
    version = 1,
    entities = [CompanyListingEntity::class]
)
abstract class StockDatabase : RoomDatabase() {
    abstract val companyListingDao: CompanyListingDao
}