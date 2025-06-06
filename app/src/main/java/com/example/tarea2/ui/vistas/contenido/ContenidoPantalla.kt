package com.example.tarea2.ui.vistas.contenido

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tarea2.EditarContenidoPan
import com.example.tarea2.Home
import com.example.tarea2.ui.componentes.DescripcionContenido
import com.example.tarea2.ui.vistas.AppViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.TextButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoPantalla(
    navController: NavController,
    contenidoId:Int,
    viewModel: ContenidoPantallaViewModel = viewModel(factory = AppViewModel.Factory)
){
    val contenido by viewModel.contenido.collectAsState()
    val corountineScope = rememberCoroutineScope()
    var mostrarMenu by remember{ mutableStateOf(false)}
    var mostrarDialogoDeBorrado by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.CargarCotenido(contenidoId)
    }

    Scaffold(
        modifier =Modifier.fillMaxSize(),
        topBar ={
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment =Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick ={
                                val popped = navController.popBackStack()
                                if(!popped) navController.navigate(Home)
                            },
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription ="Back"
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick ={mostrarMenu = true}){
                            Icon(Icons.Default.MoreVert, contentDescription = "opciones")
                            DropdownMenu(
                                expanded = mostrarMenu,
                                onDismissRequest ={mostrarMenu = false}
                            ) {
                                DropdownMenuItem(
                                    text = {Text("Editar")},
                                    onClick ={
                                        contenido?.let {
                                            navController.navigate(EditarContenidoPan(idContenido = it.id))
                                        }
                                        mostrarMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text ={Text("Eliminar")},
                                    onClick = {
                                        mostrarDialogoDeBorrado = true
                                        mostrarMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
        },
    ) {innerPadding ->
        contenido?.let{
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DescripcionContenido(it)
            }
        }
    }
    if (mostrarDialogoDeBorrado){
        AlertDialog(
            onDismissRequest ={mostrarDialogoDeBorrado = false},
            title ={ Text("Confirmar Eliminacion")},
            text = { Text("¿Estas seguro que quieres eliminar este contenido?")},
            confirmButton ={
                TextButton(
                    onClick = {
                        corountineScope.launch {
                            viewModel.BorrarContenido()
                            navController.popBackStack()
                        }
                        mostrarDialogoDeBorrado = false
                    }) {
                    Text("Si")
                }
            },
            dismissButton ={
                TextButton(onClick = {mostrarDialogoDeBorrado = false}) {
                    Text("No")
                }
            }
        )
    }
}