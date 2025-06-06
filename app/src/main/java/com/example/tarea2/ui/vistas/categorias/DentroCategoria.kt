package com.example.tarea2.ui.vistas.categorias

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tarea2.CrearContenidoPan
import com.example.tarea2.R
import com.example.tarea2.ui.componentes.CartasContenido
import com.example.tarea2.ui.componentes.TopBarConBackButton
import com.example.tarea2.ui.vistas.AppViewModel

@Composable
fun CategoriaScreen(
    navController: NavHostController,
    categoriaId: Int,
    nombreCategoria: String,
    viewModel: DentroCategoriaViewModel = viewModel(factory = AppViewModel.Factory)
){
    val contenidos by viewModel.contenidos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargaContenidosPorCategoria(nombreCategoria)
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarConBackButton(titulo = LocalContext.current.getString(R.string.app_name),
            navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(CrearContenidoPan(categoriaId))}
            ) {
                Icon(
                    painter = painterResource(R.drawable.anadir),
                    contentDescription = "Add",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    ){ innerPadding->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier =Modifier.padding(10.dp).fillMaxWidth(),
                horizontalAlignment =Alignment.CenterHorizontally
            ) {
                items(contenidos){ contenido ->
                    CartasContenido(navController = navController, contenido = contenido)
                }
            }
        }
    }
}