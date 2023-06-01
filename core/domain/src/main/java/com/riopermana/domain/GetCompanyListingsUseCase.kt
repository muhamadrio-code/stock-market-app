package com.riopermana.domain

import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.contract.CompanyListingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCompanyListingsUseCase @Inject constructor(
    private val companyListingsRepository: CompanyListingsRepository
) {
    operator fun invoke(query:String? = null) : Flow<List<CompanyListings>> {
        if(query.isNullOrEmpty()) return companyListingsRepository.getCompanyListings()
        return companyListingsRepository.getCompanyListings(query)
    }
}

