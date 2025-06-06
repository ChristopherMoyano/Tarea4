package com.example.tarea2.ui.screens.contenidos


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tarea2.R
import com.example.tarea2.ui.componentes.TopBarConBackButton
import com.example.tarea2.data.model.Contenido

@Composable
fun UpdateContenidoScreen(
    navController: NavController,
    contenidoId: Int,
    viewModel: UpdateContenidoViewModel = viewModel(factory = com.example.tarea2.ui.AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState

    // Carga los datos del contenido cuando la pantalla se muestra por primera vez
    LaunchedEffect(contenidoId) {
        viewModel.loadContenido(contenidoId)
    }

    // Navega hacia atrás cuando la actualización sea exitosa
    LaunchedEffect(uiState) {
        if (uiState is UpdateContenidoUiState.UpdateSuccess) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = { TopBarConBackButton(titulo = "Editar Contenido", navController = navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is UpdateContenidoUiState.LoadingInitialData,
                is UpdateContenidoUiState.UpdateInProgress -> CircularProgressIndicator()
                is UpdateContenidoUiState.Error -> Text("Error: ${uiState.message}")
                is UpdateContenidoUiState.Success -> {
                    // Muestra el formulario con los datos cargados
                    FormularioEditarContenido(
                        contenido = uiState.contenido,
                        onUpdate = { nombre, informacion, imagenUri ->
                            viewModel.updateContenido(
                                contenidoId = contenidoId,
                                nombre = nombre,
                                informacion = informacion,
                                imagen = imagenUri,
                                categoriaId = uiState.contenido.idCategoria
                            )
                        }
                    )
                }
                else -> {} // No muestra nada en otros estados como UpdateSuccess
            }
        }
    }
}

@Composable
fun FormularioEditarContenido(
    contenido: Contenido,
    onUpdate: (String, String, String?) -> Unit
) {
    var nombre by remember { mutableStateOf(contenido.nombreContenido) }
    var informacion by remember { mutableStateOf(contenido.informacion) }
    var imagenUri by remember { mutableStateOf<Uri?>(contenido.imagenContenido?.let { Uri.parse(it) }) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri -> imagenUri = uri }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp).align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = informacion, onValueChange = { informacion = it }, label = { Text("Información") })
            Spacer(Modifier.height(16.dp))
            Button(onClick = { launcher.launch(arrayOf("image/*")) }) { Text("Cambiar Imagen") }
            Spacer(Modifier.height(16.dp))
            AsyncImage(model = imagenUri ?: R.drawable.icon, contentDescription = null, modifier = Modifier.size(120.dp))
        }

        FloatingActionButton(
            onClick = { onUpdate(nombre, informacion, imagenUri?.toString()) },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Text("Actualizar")
        }
    }
}