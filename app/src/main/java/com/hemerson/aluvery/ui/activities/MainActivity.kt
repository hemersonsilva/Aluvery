package com.hemerson.aluvery.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hemerson.aluvery.sampledata.sampleSections
import com.hemerson.aluvery.ui.screens.HomeScreen
import com.hemerson.aluvery.ui.theme.AluveryTheme
import com.hemerson.aluvery.ui.viewmodels.HomeScreenViewModel
import com.hemerson.aluvery.ui.state.HomeUiState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(onFabClick = {
                startActivity(Intent(this, ProductFormActivity::class.java))
            }
            ) {
                val viewModel by viewModels<HomeScreenViewModel>()
                HomeScreen(viewModel)
            }
        }
    }
}

@Composable
fun App(onFabClick: () -> Unit = {}, content: @Composable () -> Unit = {}) {
    AluveryTheme {
        Surface {
            Scaffold(floatingActionButton = {
                FloatingActionButton(onClick = onFabClick) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    App {
        HomeScreen(HomeUiState(sections = sampleSections))
    }
}