package com.example.tarea2.ui.screens.categorias

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tarea2.R
import com.example.tarea2.ui.componentes.TopBarConBackButton
//import com.example.tarea2.ui.screens.categorias.CategoriaViewModel
//import com.example.tarea2.ui.screens.categorias.CategoriasUiState

@Composable
fun AddCategoriaScreen(
    navController: NavController,
    viewModel: CategoriaViewModel = viewModel(factory = com.example.tarea2.ui.AppViewModelProvider.Factory)
) {
    // 1. Obtiene el estado de la UI desde el ViewModel.
    val uiState = viewModel.categoriasUiState

    // Reinicia el estado la primera vez que la pantalla se compone para no mostrar errores antiguos.
    LaunchedEffect(Unit) {
        viewModel.resetUiState()
    }

    // 2. Navega hacia atrás automáticamente solo cuando el estado sea Success.
    LaunchedEffect(uiState) {
        if (uiState is CategoriasUiState.Success) {
            navController.popBackStack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBarConBackButton(titulo = "Nueva Categoría", navController = navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (uiState) {
                is CategoriasUiState.Loading -> {
                    // Muestra un indicador de carga mientras se guarda.
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CategoriasUiState.Error -> {
                    // Si hay un error, muestra el formulario y el mensaje de error.
                    ContenidoFormulario(
                        viewModel = viewModel,
                        modifier = Modifier.fillMaxSize(),
                        errorMessage = uiState.message
                    )
                }
                else -> {
                    // En estado Idle o Success (justo antes de navegar), muestra el formulario.
                    ContenidoFormulario(
                        viewModel = viewModel,
                        modifier = Modifier.fillMaxSize(),
                        errorMessage = null
                    )
                }
            }
        }
    }
}

@Composable
fun ContenidoFormulario(
    viewModel: CategoriaViewModel,
    modifier: Modifier = Modifier,
    errorMessage: String?
) {
    // 3. Usa estado local para los campos del formulario.
    var nombre by remember { mutableStateOf("") }
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

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Nueva categoría", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de Categoría") },
                isError = errorMessage != null
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = { launcher.launch(arrayOf("image/*")) }
            ) {
                Text("Seleccionar icono")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (imagenUri != null) {
                AsyncImage(
                    model = imagenUri,
                    contentDescription = "Icono Categoria",
                    modifier = Modifier.size(100.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.icon), // Reemplaza con un recurso tuyo
                    contentDescription = "Imagen por defecto",
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        FloatingActionButton(
            onClick = {
                // 4. Llama al ViewModel con los datos del estado local.
                viewModel.addCategoria(nombre, imagenUri?.toString())
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("Guardar")
        }
    }
}