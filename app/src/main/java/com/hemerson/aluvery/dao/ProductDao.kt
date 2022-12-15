package com.hemerson.aluvery.dao

import androidx.compose.runtime.mutableStateListOf
import com.hemerson.aluvery.model.Product
import com.hemerson.aluvery.sampledata.sampleProducts

class ProductDao {
    companion object{
        private val products = mutableStateListOf(*sampleProducts.toTypedArray())
    }

    fun products() = products.toList()
    fun save(product: Product) {
        products.add(product)
    }
}