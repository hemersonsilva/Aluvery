package com.hemerson.aluvery.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    searchText: String,
    modifier: Modifier = Modifier,
    onSearchChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = { newValue ->
            onSearchChange(newValue)
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(100),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "")
        },
        label = {
            Text(text = "Produto")
        },
        placeholder = {
            Text(text = "O que você deseja?")
        }
    )
}