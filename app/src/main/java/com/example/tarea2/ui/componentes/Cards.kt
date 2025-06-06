package com.example.tarea2.ui.componentes

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.tarea2.ContenidoPan
import com.example.tarea2.data.entity.Contenido
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.tarea2.CategoriaPan
import com.example.tarea2.R
import com.example.tarea2.data.entity.Categoria
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Text

@Composable
fun CartasContenido(
    navController: NavHostController,
    contenido: Contenido
)
{
    Card(
        onClick ={
            navController.navigate(ContenidoPan(contenido.id))
        },
        colors = CardColors(
            containerColor = Color(26,40,65,255),
            contentColor = Color.White,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.Transparent
        ),
        modifier = Modifier.padding(10.dp)
    ){
        Column {
            if(contenido.imagenContenido !=null){
                AsyncImage(
                    model = Uri.parse(contenido.imagenContenido),
                    contentDescription = contenido.nombreContenido,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(50.dp)
                )
            }
            else{
                Image(
                    painter = painterResource(R.drawable.bajoconstruccion),
                    contentDescription = contenido.nombreContenido,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(50.dp)
                )
            }
            Text(
                text = contenido.nombreContenido,
                color = Color.White,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Composable
fun ListaCategoriasCartas(
    categorias:List<Categoria>,
    navController: NavHostController
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
    navController: NavHostController
){
    Card(
        onClick ={navController.navigate(CategoriaPan(categoria.id,categoria.nombre))},
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
            if(categoria.imagenCategoria != null){
                AsyncImage(
                    model = Uri.parse(categoria.imagenCategoria),
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
                text = contenido.info,
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }
    }
}