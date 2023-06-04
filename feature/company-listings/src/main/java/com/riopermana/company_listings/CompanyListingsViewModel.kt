package com.riopermana.company_listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riopermana.common.Resource
import com.riopermana.domain.GetCompanyListingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class CompanyListingsViewModel @Inject constructor(
    getCompanyListingsUseCase: GetCompanyListingsUseCase,
) : ViewModel() {

    val query = MutableStateFlow<String?>(null)

    @OptIn(FlowPreview::class)
    val resourceState = query
        .debounce(200)
        .flatMapLatest(getCompanyListingsUseCase::invoke)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Resource.Loading
        )

    fun getCompanyListingsByQuery(query: String?) {
        this.query.value = query
    }

    fun clearQuery() {
        this.query.value = null
    }

}

