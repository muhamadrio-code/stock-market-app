package com.riopermana.company_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.riopermana.common.doWhen
import com.riopermana.data.model.CompanyListings
import com.riopermana.ui.CompanyItem
import com.riopermana.ui.DefaultLoadingIndicator
import com.riopermana.ui.SearchTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingsScreen(
    modifier: Modifier = Modifier,
    viewModel: CompanyListingsViewModel = hiltViewModel(),
) {
    val lazyListState = rememberLazyListState()
    val companyListingsState by viewModel.resourceState.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                onQueryChange = viewModel::getCompanyListingsByQuery,
                query = query,
                onTrailingIconClick = viewModel::clearQuery,
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->
        Spacer(modifier = Modifier.height(16.dp))
        companyListingsState.doWhen(
            loading = {
                DefaultLoadingIndicator(Modifier.fillMaxSize())
            },
            success = { items ->
                LazyColumn(
                    modifier = modifier.padding(horizontal = 16.dp),
                    state = lazyListState,
                    content = {
                        companyItems(items)
                    },
                    contentPadding = paddingValues
                )
            },
            error = { message, _, _ ->
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = message,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        )
    }
}


private fun LazyListScope.companyItems(items: List<CompanyListings>) {
    items(items.size) { index ->
        CompanyItem(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (index % 2 == 0) {
                        Color.Transparent
                    } else {
                        MaterialTheme.colorScheme.primary.copy(.1f)
                    }
                )
                .padding(8.dp),
            companyListings = items[index]
        )
    }
}