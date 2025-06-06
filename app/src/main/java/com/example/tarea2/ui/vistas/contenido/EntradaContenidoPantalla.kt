package com.example.tarea2.ui.vistas.contenido

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tarea2.ui.vistas.AppViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tarea2.ui.componentes.TopBarConBackButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import com.example.tarea2.ui.componentes.CategoryDropdown
import com.example.tarea2.ui.componentes.CommonFields
import com.example.tarea2.ui.componentes.ImagePickerSection

@Composable
fun EntradaContenidoPantalla(
    navController: NavHostController,
    categoriaId: Int,
    viewModel: EntradaContenidoViewModel = viewModel(factory = AppViewModel.Factory)
){
    val nombre by viewModel.nombre.collectAsState()
    val informacion by viewModel.informacion.collectAsState()
    val imagen by viewModel.imagen.collectAsState()
    val categorias by viewModel.categorias.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()

    LaunchedEffect(categorias,categoriaSeleccionada){
        if(categoriaSeleccionada == null &&  categorias.isNotEmpty()){
            val categoriaInicial = categorias.firstOrNull{it.id == categoriaId}
            categoriaInicial?.let {viewModel.enCategoriaSeleccionada(it)}
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult ={uri -> uri?.let { viewModel.enCambiarImagen(it.toString()) }  }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBarConBackButton(titulo = "Agregue Contenido",navController)},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    categoriaSeleccionada?.let {
                        viewModel.guardarContenido(it.nombre)
                        navController.popBackStack()
                    }
                },
                modifier =Modifier.padding(20.dp)
            ) {
                Text("Guardar")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(24.dp))

            CategoryDropdown(categorias, categoriaSeleccionada, viewModel::enCategoriaSeleccionada)

            Spacer(modifier = Modifier.height(16.dp))

            CommonFields(nombre, informacion,viewModel::enCambiarNombre,viewModel::enCambiarInformacion)

            ImagePickerSection(imagen) {
                launcher.launch(arrayOf("image/*"))
            }
        }
    }


}