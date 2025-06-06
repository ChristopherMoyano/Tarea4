package com.example.tarea2.ui.vistas.contenido

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tarea2.ui.componentes.CategoryDropdown
import com.example.tarea2.ui.componentes.CommonFields
import com.example.tarea2.ui.componentes.ImagePickerSection
import com.example.tarea2.ui.componentes.TopBarConBackButton
import com.example.tarea2.ui.vistas.AppViewModel

@Composable
fun ContenidoEditarPantalla(
    navController: NavHostController,
    contenidoId: Int,
    viewModel: EditarContenidoViewModel = viewModel(factory = AppViewModel.Factory),
    onSaveSuccess:() -> Unit
){
    val nombre by viewModel.nombre.collectAsState()
    val information by viewModel.informacion.collectAsState()
    val imagen by viewModel.imagen.collectAsState()
    val categorias by viewModel.categorias.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()

    LaunchedEffect(contenidoId) {
        viewModel.CargarContenidoPorId(contenidoId)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult ={ uri -> uri?.let { viewModel.cambiarImagen(it.toString())}}
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {TopBarConBackButton(titulo = "Editar Contenido",
            navController = navController)},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.actualizarContenido()
                    onSaveSuccess()
                },
                modifier =Modifier.padding(20.dp)
            ) {
                Text("Guardar cambios")
            }
        }
    ) {innerPadding ->
        Column (
            modifier = Modifier.
            padding(innerPadding).
            padding(horizontal = 16.dp).
            fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(24.dp))

            CategoryDropdown(categorias, categoriaSeleccionada,viewModel::cambiarCategoria)

            Spacer(modifier = Modifier.height(16.dp))

            CommonFields(nombre,information, viewModel::cambiarNombre,viewModel::cambiarInformacion)

            ImagePickerSection(imagen) {
                launcher.launch(arrayOf("image/*"))
            }
        }
    }
}