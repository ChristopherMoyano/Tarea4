package com.example.tarea2.ui.screens.categorias

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tarea2.R
import com.example.tarea2.ui.componentes.TopBarConBackButton
import androidx.compose.material3.Text
import com.example.tarea2.AddCategoria
import com.example.tarea2.ui.componentes.ListaCategoriasCartas

@Composable
fun CategoriasScreen(
    viewModel: CategoriaViewModel = viewModel(
        factory = com.example.tarea2.ui.AppViewModelProvider.Factory
    ),
    navController: NavController
){
    LaunchedEffect(Unit) {
        viewModel.refreshCategorias()
    }
    Scaffold (modifier = Modifier.fillMaxSize(),
        topBar = { TopBarConBackButton(titulo = LocalContext.current.getString(R.string.app_name),
            navController = navController, showBack = false)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(AddCategoria)}
            ) {
                Icon(
                    painter = painterResource(R.drawable.anadir),
                    contentDescription = "Add",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    ){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            when (val state = viewModel.categoriasUiState)
            {
                is CategoriasUiState.Loading ->{
                    Text(text ="Cargando categorias...")
                }
                is CategoriasUiState.Error ->{
                    Text(text = "Error: ${state.message}")
                }

                is CategoriasUiState.Success ->{
                    ListaCategoriasCartas(state.categorias,navController)
                }

                else -> {}
            }

        }
    }
}