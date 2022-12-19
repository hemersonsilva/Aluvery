package com.hemerson.aluvery.uistate

import com.hemerson.aluvery.model.Product

data class HomeUiState(
    val sections: Map<String, List<Product>> = emptyMap(),
    val searchedProducts: List<Product> = emptyList(),
    val searchedText: String = "",
    val onSearchChange: (String) -> Unit = {}
) {
    fun isShowSections(): Boolean {
        return searchedText.isBlank()
    }
}