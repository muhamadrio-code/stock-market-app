package com.riopermana.data.mapper

import com.riopermana.data.model.CompanyListings
import com.riopermana.database.entities.CompanyListingEntity
import com.riopermana.network.dto.CompanyListingDto

fun CompanyListingDto.toEntity(): CompanyListingEntity = CompanyListingEntity(
    symbol = this.symbol ?: "",
    name = this.name ?: "",
    exchange = this.exchange ?: ""
)

fun List<CompanyListingEntity>.toListExternalModel(): List<CompanyListings> =
    mapNotNull(CompanyListingEntity::toListExternalModel)

fun CompanyListingEntity.toListExternalModel(): CompanyListings = CompanyListings(
    name = this.name,
    symbol = this.symbol,
    exchange = this.exchange
)