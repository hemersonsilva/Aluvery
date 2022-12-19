package com.hemerson.aluvery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hemerson.aluvery.R
import com.hemerson.aluvery.model.Product
import com.hemerson.aluvery.ui.theme.AluveryTheme
import com.hemerson.aluvery.ui.state.ProductFormUiState
import java.math.BigDecimal
import java.text.DecimalFormat


@Composable
fun ProductFormScreen(
    onSaveClick: (Product) -> Unit = {}
){
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var url by rememberSaveable {
        mutableStateOf("")
    }
    var price by rememberSaveable {
        mutableStateOf("")
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }
    val formatter = remember {
        DecimalFormat("#.##")
    }

    ProductFormScreen(
        state = ProductFormUiState(
            url = url,
            name = name,
            price = price,
            description = description,
            onUrlChange = {
                url = it
            },
            onNameChange = {
                name = it
            },
            onPriceChange = {
                try {
                    price = formatter.format(BigDecimal(it))
                } catch (e: IllegalArgumentException) {
                    if (it.isBlank()) {
                        price = it
                    }
                }
            },
            onDescriptionChange = {
                description = it
            }
        ),
        onSaveClick = {
            val convertedPrice = try {
                BigDecimal(price)
            } catch (e: NumberFormatException) {
                BigDecimal.ZERO
            }
            val product = Product(
                name = name,
                image = url,
                price = convertedPrice,
                description = description
            )
            onSaveClick(product)
        }
    )

}

@Composable
fun ProductFormScreen(
    state: ProductFormUiState = ProductFormUiState(),
    onSaveClick: () -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val url = state.url
        val name = state.name
        val price = state.price
        val description = state.description


        Spacer(modifier = Modifier)
        Text(
            text = "Criando um Produto", Modifier.fillMaxWidth(), fontSize = 28.sp
        )

        if (state.isShowPreview) {
            AsyncImage(
                model = url,
                contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder)
            )
        }

        TextField(
            value = url,
            onValueChange = state.onUrlChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Url da imagem")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next
            )
        )
        TextField(
            value = name,
            onValueChange = state.onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Nome")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            )
        )
        TextField(
            value = price,
            onValueChange = state.onPriceChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Preço")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = description,
            onValueChange = state.onDescriptionChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp), label = {
                Text(text = "Descrição")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSaveClick
        ) {
            Text(text = "Salvar")
        }
        Spacer(modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductFormScreenPreview() {
    AluveryTheme {
        ProductFormScreen()
    }
}