package com.riopermana.network.dto

/**
 * Data class representing a company listing.
 * This class is a DTO that holds information about a company listing.
 * It contains properties for the symbol, name, and exchange of the company.
 *
 * @property symbol The symbol or ticker of the company.
 * @property name The name of the company.
 * @property exchange The stock exchange where the company is listed.
 * */
data class CompanyListingDto(
    val symbol:String? = null,
    val name:String? = null,
    val exchange:String? = null,
)
