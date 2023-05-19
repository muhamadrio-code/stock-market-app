package com.riopermana.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entity representing a company listing in the local database.
 * It contains properties for the [symbol], [name], and [exchange] of the company.
 */
@Entity(
    tableName = "company_listings"
)
data class CompanyListingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @field:ColumnInfo("symbol")
    val symbol:String? = null,
    @field:ColumnInfo("name")
    val name:String? = null,
    @field:ColumnInfo("exchange")
    val exchange:String? = null,
)