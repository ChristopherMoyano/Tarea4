package com.example.tarea2.ui.screens.contenidos

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tarea2.UpdateContenido
import com.example.tarea2.ui.componentes.DescripcionContenido


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoDetailScreen(
    navController: NavController,
    contenidoId: Int,
    viewModel: ContenidoDetailViewModel = viewModel(factory = com.example.tarea2.ui.AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState
    var mostrarMenu by remember { mutableStateOf(false) }
    var mostrarDialogoDeBorrado by remember { mutableStateOf(false) }

    // Carga los datos la primera vez
    LaunchedEffect(contenidoId) {
        viewModel.loadContenido(contenidoId)
    }

    // Reacciona al estado de borrado exitoso para navegar hacia atrás
    LaunchedEffect(uiState) {
        if (uiState is ContenidoDetailUiState.DeleteSuccess) {
            navController.popBackStack()
        }
    }

    if (mostrarDialogoDeBorrado) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoDeBorrado = false },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Estás seguro de que quieres eliminar este contenido?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteContenido(contenidoId)
                        mostrarDialogoDeBorrado = false
                    }
                ) { Text("Sí") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoDeBorrado = false }) { Text("No") }
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    // Solo muestra el menú si la carga fue exitosa
                    if (uiState is ContenidoDetailUiState.Success) {
                        IconButton(onClick = { mostrarMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                        }
                        DropdownMenu(
                            expanded = mostrarMenu,
                            onDismissRequest = { mostrarMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Editar") },
                                onClick = {
                                    navController.navigate(UpdateContenido(contenidoId = contenidoId))
                                    mostrarMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Eliminar") },
                                onClick = {
                                    mostrarDialogoDeBorrado = true
                                    mostrarMenu = false
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is ContenidoDetailUiState.Loading -> CircularProgressIndicator()
                is ContenidoDetailUiState.Error -> Text(text = "Error: ${uiState.message}")
                is ContenidoDetailUiState.Success -> {
                    DescripcionContenido(uiState.contenido)
                }
                // No es necesario manejar DeleteSuccess aquí porque el LaunchedEffect ya lo hace
                else -> {}
            }
        }
    }
}