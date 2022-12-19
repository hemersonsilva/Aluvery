package com.hemerson.aluvery.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hemerson.aluvery.dao.ProductDao
import com.hemerson.aluvery.model.Product
import com.hemerson.aluvery.sampledata.sampleCandies
import com.hemerson.aluvery.sampledata.sampleDrinks
import com.hemerson.aluvery.sampledata.sampleProducts
import com.hemerson.aluvery.uistate.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

    private val dao = ProductDao()

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onSearchChange = {
                    _uiState.value = _uiState.value.copy(
                        searchedText = it,
                        searchedProducts = searchedProducts(it)
                    )
                }
            )
        }

        viewModelScope.launch {
            dao.products().collect { products ->
                _uiState.value = _uiState.value.copy(
                    sections = mapOf(
                        "Todos produtos" to products,
                        "Promoções" to sampleDrinks + sampleCandies,
                        "Doces" to sampleCandies,
                        "Bebidas" to sampleDrinks,
                    ),
                    searchedProducts = searchedProducts(_uiState.value.searchedText)
                )
            }
        }
    }

   private fun containsInNameOrDescription(text: String) = { product: Product ->
        product.name.contains(
            text,
            ignoreCase = true
        ) || product.description?.contains(
            text,
            ignoreCase = true
        ) ?: false
    }

    private fun searchedProducts(text: String): List<Product> =
        if (text.isNotEmpty()) {
        sampleProducts.filter(containsInNameOrDescription(text)) + dao.products().value.filter(
            containsInNameOrDescription(text)
        )
    } else emptyList()

}