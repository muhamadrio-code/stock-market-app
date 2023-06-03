package com.riopermana.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Database entity representing a company listing in the local database.
 * It contains properties for the [symbol], [name], and [exchange] of the company.
 */
@Entity(
    tableName = "company_listings",
    primaryKeys = ["name", "symbol", "exchange"]
)
data class CompanyListingEntity(
    @field:ColumnInfo("symbol")
    val symbol:String,
    @field:ColumnInfo("name")
    val name:String,
    @field:ColumnInfo("exchange")
    val exchange:String
)