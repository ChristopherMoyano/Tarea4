package com.example.tarea2.ui.screens.contenidos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tarea2.AddContenido
import com.example.tarea2.R
import com.example.tarea2.ui.componentes.CartasContenido
import com.example.tarea2.ui.componentes.TopBarConBackButton


@Composable
fun ContenidoScreen(
    navController: NavController,
    categoriaId: Int,
    nombreCategoria: String,
    // Usamos el nuevo ViewModel
    viewModel: ContenidoViewModel = viewModel(factory = com.example.tarea2.ui.AppViewModelProvider.Factory)
) {
    // 1. Observa el estado de la UI
    val uiState = viewModel.contenidoUiState

    // 2. Llama para cargar los contenidos solo una vez, usando el ID de la categoría.
    LaunchedEffect(categoriaId) {
        viewModel.getContenidos(categoriaId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarConBackButton(
                titulo = nombreCategoria, // Muestra el nombre de la categoría
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(AddContenido(categoriaId = categoriaId)) }) {  //ruta añadir contenido
                Icon(
                    painter = painterResource(R.drawable.anadir),
                    contentDescription = "Añadir Contenido",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // 3. La UI reacciona al estado del ViewModel
            when (uiState) {
                is ContenidoUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is ContenidoUiState.Error -> {
                    Text(text = "Error: ${uiState.message}")
                }
                is ContenidoUiState.Success -> {
                    if (uiState.contenidos.isEmpty()) {
                        Text(text = "No hay contenidos en esta categoría.")
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(uiState.contenidos) { contenido ->
                                CartasContenido(
                                    navController = navController,
                                    contenido = contenido,
                                    // Pasamos la función de borrado al componente de la tarjeta
                                    onDelete = {
                                        viewModel.deleteContenido(contenido.id, categoriaId)
                                    }
                                )
                            }
                        }
                    }
                }
                is ContenidoUiState.Idle -> {
                    // No hace nada, espera a que se carguen los datos
                }
            }
        }
    }
}