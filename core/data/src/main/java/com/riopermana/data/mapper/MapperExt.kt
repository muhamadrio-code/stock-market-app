package com.riopermana.data.mapper

import com.riopermana.data.model.CompanyListings
import com.riopermana.database.entities.CompanyListingEntity
import com.riopermana.network.dto.CompanyListingDto

fun CompanyListingDto.toEntity(): CompanyListingEntity = CompanyListingEntity(
    name = this.name,
    symbol = this.symbol,
    exchange = this.exchange
)

fun List<CompanyListingEntity>.toListExternalModel(): List<CompanyListings> =
    mapNotNull(CompanyListingEntity::toListExternalModel)

fun CompanyListingEntity.toListExternalModel(): CompanyListings? {
    if (name == null) return null
    if (exchange == null) return null
    if (symbol == null) return null

    return CompanyListings(
        id = this.id,
        name = this.name!!,
        symbol = this.symbol!!,
        exchange = this.exchange!!
    )
}