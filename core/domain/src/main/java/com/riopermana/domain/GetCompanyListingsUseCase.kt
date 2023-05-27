package com.riopermana.domain

import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.contract.CompanyListingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetCompanyListingsUseCase @Inject constructor(
    private val companyListingsRepository: CompanyListingsRepository
) {
    operator fun invoke() : Flow<List<CompanyListings>> {
        return companyListingsRepository.getCompanyListings()
    }

    operator fun invoke(query:String) : Flow<List<CompanyListings>> {
        if(query.isEmpty()) return emptyFlow()
        return companyListingsRepository.getCompanyListings(query)
    }
}

