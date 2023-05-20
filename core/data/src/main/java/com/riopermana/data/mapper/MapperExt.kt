package com.riopermana.data.mapper

import com.riopermana.database.entities.CompanyListingEntity
import com.riopermana.network.dto.CompanyListingDto

fun CompanyListingDto.toEntity() : CompanyListingEntity = CompanyListingEntity(
    name = this.name,
    symbol = this.symbol,
    exchange = this.exchange
)