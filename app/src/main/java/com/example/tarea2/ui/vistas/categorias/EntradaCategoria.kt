package com.example.tarea2.ui.vistas.categorias

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tarea2.R
import com.example.tarea2.ui.vistas.AppViewModel
import coil.compose.AsyncImage
import com.example.tarea2.ui.componentes.TopBarConBackButton

@Composable
fun CrearCategoriaScreen(
    navController: NavController,
    viewModel: EntradaCategoriaViewModel = viewModel(factory = AppViewModel.Factory)
){
    val nombre by viewModel.nombre.collectAsState()
    val imagen by viewModel.imagenUri.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.cambiarImagen(it.toString())
            }
        }
    )
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {TopBarConBackButton(titulo = context.getString(R.string.app_name),
            navController = navController)}
    ){ innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding).fillMaxWidth(),
            horizontalAlignment =Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text("Nueva categoría", style = MaterialTheme.typography.headlineSmall)

            FormCat(nombre = nombre,
                cambiarnombre = viewModel::cambiarNombre,
                mensajeError = mensajeError)

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick ={
                    launcher.launch(arrayOf("image/*"))
                },
                modifier = Modifier.padding(16.dp)
            ){
                Text("Seleccionar icono")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if(imagen !=null){
                AsyncImage(
                    model = Uri.parse(imagen),
                    contentDescription = "Icono Categoria",
                    modifier = Modifier.size(100.dp)
                )
            }
            else{
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.otros),
                    contentDescription = "Imagen por defecto",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            FloatingActionButton(
                onClick = {
                    viewModel.guardarCategoria()
                    navController.popBackStack()
                },
                modifier = Modifier.padding(20.dp)
            ) {
                Text("Guardar")
            }
        }
    }
}

@Composable
fun FormCat(
    nombre: String,
    cambiarnombre:(String)-> Unit,
    mensajeError:String
){
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            value = nombre,
            onValueChange = cambiarnombre,
            label = { Text("Nombre de Categoría")}
        )
        if(mensajeError.isNotEmpty()){
            Text(
                text = mensajeError,
                color = Color.Red,
                modifier = Modifier.padding(top= 4.dp)
            )
        }
    }
}