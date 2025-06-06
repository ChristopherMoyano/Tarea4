package com.example.tarea2.ui.componentes

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.tarea2.R
import com.example.tarea2.data.model.Categoria
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Text
import com.example.tarea2.ContenidoDetail
import com.example.tarea2.ContenidosRoute
import com.example.tarea2.data.model.Contenido

@Composable
fun CartasContenido(
    navController: NavController,
    contenido: Contenido,
    onDelete: () -> Unit // <-- 1. AÑADIMOS EL NUEVO PARÁMETRO
) {
    Card(
        onClick = {
            navController.navigate(ContenidoDetail(contenidoId = contenido.id)) // ruta para irnos a una pantalla de contenido con la descripcion
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(26, 40, 65, 255)
        ),
        modifier = Modifier.padding(10.dp).fillMaxWidth()
    ) {
        // 2. Usamos un Box para poder posicionar el botón de borrado en una esquina.
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                if (contenido.imagenContenido != null) {
                    AsyncImage(
                        model = Uri.parse(contenido.imagenContenido),
                        contentDescription = contenido.nombreContenido,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(60.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.bajoconstruccion),
                        contentDescription = contenido.nombreContenido,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(60.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = contenido.nombreContenido,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // 3. AÑADIMOS EL BOTÓN DE BORRADO
            IconButton(
                onClick = onDelete, // Llama a la función que nos pasaron
                modifier = Modifier.align(Alignment.TopEnd) // Lo posiciona arriba a la derecha
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.White.copy(alpha = 0.7f) // Le damos un color para que sea visible
                )
            }
        }
    }
}

@Composable
fun ListaCategoriasCartas(
    categorias:List<Categoria>,
    navController: NavController
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(categorias){ categoria ->
            CategoriaCartasItem(categoria,navController)
        }
    }
}

@Composable
fun CategoriaCartasItem(
    categoria:Categoria,
    navController: NavController
){
    Card(
        onClick ={navController.navigate(
            ContenidosRoute(
                categoriaId = categoria.id,
                nombreCategoria = categoria.nombre)
        ) },  //me voy al contenido de la categoria
        modifier = Modifier.padding(10.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if(categoria.imagen != null){
                AsyncImage(
                    model = Uri.parse(categoria.imagen),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }
            else{
                Icon(
                    painter = painterResource(R.drawable.otros),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }

            Text(
                text = categoria.nombre,
                color = Color.White,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Composable
fun DescripcionContenido(contenido: Contenido){
    LazyColumn(
        modifier = Modifier.padding(10.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            if(contenido.imagenContenido != null){
                AsyncImage(
                    model = Uri.parse(contenido.imagenContenido),
                    contentDescription = contenido.nombreContenido,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.aspectRatio(12f/9f).clip(RoundedCornerShape(8.dp))
                )
            }
            else{
                Image(
                    painter = painterResource(R.drawable.bajoconstruccion),
                    contentDescription = contenido.nombreContenido,
                    contentScale = ContentScale.Fit,
                    modifier =Modifier.aspectRatio(12f/9f).clip(RoundedCornerShape(8.dp))
                )
            }
            Text(
                text = contenido.nombreContenido,
                modifier =Modifier.padding(8.dp),
                color = Color.White
            )
            Text(
                text = contenido.informacion,
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }
    }
}