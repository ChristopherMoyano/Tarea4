package com.example.tarea2.ui.vistas


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.tarea2.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tarea2.CrearCategoriaPan
import com.example.tarea2.ui.componentes.ListaCategoriasCartas
import com.example.tarea2.ui.componentes.TopBarConBackButton
import com.example.tarea2.ui.vistas.categorias.EntradaCategoriaViewModel


@Composable
fun HomeScreen(navController: NavHostController,
               viewModel: EntradaCategoriaViewModel = viewModel(factory = AppViewModel.Factory)
){

    val categorias by viewModel.categorias.collectAsState(initial = emptyList())

    Scaffold (modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarConBackButton(titulo = LocalContext.current.getString(R.string.app_name),
            navController = navController, showBack = false)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(CrearCategoriaPan)}
            ) {
                Icon(
                    painter = painterResource(R.drawable.anadir),
                    contentDescription = "Add",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding).fillMaxWidth(),
            horizontalAlignment =Alignment.CenterHorizontally,
        ){
            ListaCategoriasCartas(categorias,navController)
        }

        }
}


