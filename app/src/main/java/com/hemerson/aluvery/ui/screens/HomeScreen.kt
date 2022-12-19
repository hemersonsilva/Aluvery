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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hemerson.aluvery.model.Product
import com.hemerson.aluvery.sampledata.sampleCandies
import com.hemerson.aluvery.sampledata.sampleDrinks
import com.hemerson.aluvery.sampledata.sampleProducts
import com.hemerson.aluvery.sampledata.sampleSections
import com.hemerson.aluvery.ui.components.CardProductItem
import com.hemerson.aluvery.ui.components.ProductsSection
import com.hemerson.aluvery.ui.components.SearchTextField
import com.hemerson.aluvery.ui.theme.AluveryTheme
import com.hemerson.aluvery.uistate.HomeUiState


@Composable
fun HomeScreen(products: List<Product>){

    val sections = mapOf(
        "Todos produtos" to products,
        "Promoções" to sampleDrinks + sampleCandies,
        "Doces" to sampleCandies,
        "Bebidas" to sampleDrinks,
    )
    var text by rememberSaveable { mutableStateOf("") }

    fun containsInNameOrDescription() = { product: Product ->
        product.name.contains(
            text,
            ignoreCase = true
        ) || product.description?.contains(
            text,
            ignoreCase = true
        ) ?: false
    }

    val searchedProducts = remember(products, text) {
        if (text.isNotEmpty()) {
            sampleProducts.filter(containsInNameOrDescription()) + products.filter(
                containsInNameOrDescription()
            )
        } else emptyList()

    }

    val state = remember(products, text) {
        HomeUiState(
            sections = sections,
            searchedProducts = searchedProducts,
            searchedText = text,
            onSearchChange = {
                text = it
            }
        )
    }

    HomeScreen(state)
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