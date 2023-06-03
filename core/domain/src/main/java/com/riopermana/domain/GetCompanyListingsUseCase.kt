package com.riopermana.domain

import androidx.annotation.StringRes
import com.riopermana.common.Resource
import com.riopermana.common.helper.ResourcesHelper
import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.contract.CompanyListingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class GetCompanyListingsUseCase @Inject constructor(
    private val companyListingsRepository: CompanyListingsRepository,
    private val resourcesHelper: ResourcesHelper<String>,
) {

    operator fun invoke(query: String? = null): Flow<Resource<List<CompanyListings>>> =
        channelFlow {
            send(Resource.Loading)
            if (query.isNullOrEmpty()) {
                companyListingsRepository.getCompanyListings()
                    .collect {
                        if (it.isNotEmpty()) {
                            send(Resource.Success(it))
                        }
                    }
            } else {
                companyListingsRepository.getCompanyListings(query)
                    .collect {
                        if (it.isEmpty()) {
                            send(
                                Resource.Error(
                                    "${createMessage(R.string.no_matches_query)} \"$query\"",
                                    data = it
                                )
                            )
                        } else {
                            send(Resource.Success(it))
                        }
                    }
            }
        }

    private fun createMessage(@StringRes id: Int): String =
        resourcesHelper.fromResId(id)

}

