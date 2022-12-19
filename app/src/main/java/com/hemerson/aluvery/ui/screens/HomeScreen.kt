package com.hemerson.aluvery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hemerson.aluvery.sampledata.sampleSections
import com.hemerson.aluvery.ui.components.CardProductItem
import com.hemerson.aluvery.ui.components.ProductsSection
import com.hemerson.aluvery.ui.components.SearchTextField
import com.hemerson.aluvery.ui.theme.AluveryTheme
import com.hemerson.aluvery.ui.viewmodels.HomeScreenViewModel
import com.hemerson.aluvery.ui.state.HomeUiState


@Composable
fun HomeScreen(
    viewModel :HomeScreenViewModel
){
    val state by viewModel.uiState.collectAsState()
    HomeScreen(state = state)
}

@Composable
fun HomeScreen(
    state: HomeUiState = HomeUiState()
) {
    Column {

        val sections = state.sections
        val text = state.searchedText
        val searchedProducts = state.searchedProducts

        SearchTextField(
            searchText = text,
            onSearchChange = state.onSearchChange,
        )

        LazyColumn(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            if (state.isShowSections()) {
                for (section in sections) {
                    val title = section.key
                    val products = section.value
                    item {
                        ProductsSection(
                            title = title,
                            products = products
                        )
                    }
                }
            } else {
                items(searchedProducts) { p ->
                    CardProductItem(
                        product = p,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    AluveryTheme {
        Surface {
            HomeScreen(HomeUiState(sections = sampleSections))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenWithSearchTextPreview() {
    AluveryTheme {
        Surface {
            HomeScreen(
                state = HomeUiState(searchedText = "Pizza", sections = sampleSections)

            )
        }
    }
}