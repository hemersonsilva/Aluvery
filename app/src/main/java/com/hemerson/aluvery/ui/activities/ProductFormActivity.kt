package com.hemerson.aluvery.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import com.hemerson.aluvery.ui.screens.ProductFormScreen
import com.hemerson.aluvery.ui.theme.AluveryTheme
import com.hemerson.aluvery.ui.viewmodels.ProductFormScreenViewModel

class ProductFormActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ProductFormScreenViewModel by viewModels()
            Surface {
                AluveryTheme {
                    ProductFormScreen(
                        viewModel = viewModel,
                        onSaveClick = { finish() }
                    )
                }
            }
        }
    }
}