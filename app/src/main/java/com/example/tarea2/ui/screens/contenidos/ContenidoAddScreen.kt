package com.example.tarea2.ui.screens.contenidos

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tarea2.R
import com.example.tarea2.ui.componentes.TopBarConBackButton

@Composable
fun AddContenidoScreen(
    navController: NavController,
    categoriaId: Int, // Recibimos el ID de la categoría a la que pertenece el nuevo contenido
    viewModel: ContenidoViewModel = viewModel(factory = com.example.tarea2.ui.AppViewModelProvider.Factory)
) {
    val uiState = viewModel.contenidoUiState

    // Reinicia el estado para no mostrar errores/éxitos de operaciones anteriores.
    LaunchedEffect(Unit) {
        viewModel.resetUiState()
    }

    // Navega hacia atrás automáticamente cuando el estado sea Success.
    LaunchedEffect(uiState) {
        if (uiState is ContenidoUiState.Success) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = { TopBarConBackButton(titulo = "Nuevo Contenido", navController = navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (uiState) {
                is ContenidoUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ContenidoUiState.Error -> {
                    FormularioContenido(
                        categoriaId = categoriaId,
                        viewModel = viewModel,
                        errorMessage = uiState.message
                    )
                }
                else -> { // Idle o Success
                    FormularioContenido(
                        categoriaId = categoriaId,
                        viewModel = viewModel,
                        errorMessage = null
                    )
                }
            }
        }
    }
}

@Composable
fun FormularioContenido(
    categoriaId: Int,
    viewModel: ContenidoViewModel,
    errorMessage: String?
) {
    var nombre by remember { mutableStateOf("") }
    var informacion by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                imagenUri = it
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Contenido") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = informacion,
                onValueChange = { informacion = it },
                label = { Text("Información Adicional") },
                modifier = Modifier.fillMaxWidth()
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { launcher.launch(arrayOf("image/*")) }) {
                Text("Seleccionar Imagen")
            }

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = imagenUri ?: R.drawable.bajoconstruccion, // Muestra la imagen seleccionada o una por defecto
                contentDescription = "Imagen del Contenido",
                modifier = Modifier.size(120.dp)
            )
        }

        FloatingActionButton(
            onClick = {
                viewModel.addContenido(
                    nombre = nombre,
                    informacion = informacion,
                    imagen = imagenUri?.toString(),
                    categoriaId = categoriaId
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("Guardar")
        }
    }
}