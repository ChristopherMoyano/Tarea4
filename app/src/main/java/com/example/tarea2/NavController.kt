package com.example.tarea2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tarea2.ui.vistas.HomeScreen
import kotlinx.serialization.Serializable
import com.example.tarea2.ui.vistas.categorias.CategoriaScreen
import com.example.tarea2.ui.vistas.categorias.CrearCategoriaScreen
import com.example.tarea2.ui.vistas.contenido.ContenidoEditarPantalla
import com.example.tarea2.ui.vistas.contenido.ContenidoPantalla
import com.example.tarea2.ui.vistas.contenido.EntradaContenidoPantalla

@Serializable
object Home

@Serializable
object CrearCategoriaPan

@Serializable
data class CategoriaPan(val idCategoria: Int, val nombreCategoria: String)

@Serializable
data class CrearContenidoPan(val idCategoria: Int)

@Serializable
data class ContenidoPan(val idContenido: Int)

@Serializable
data class EditarContenidoPan(val idContenido: Int)

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home){
        composable<Home>{
            HomeScreen(navController = navController)
        }

        composable<CrearCategoriaPan>{
            CrearCategoriaScreen(navController = navController)
        }

        composable<CategoriaPan>{ backstackEntry ->
            val args = backstackEntry.toRoute<CategoriaPan>()

            CategoriaScreen(
                navController = navController,
                categoriaId = args.idCategoria,
                nombreCategoria = args.nombreCategoria
            )
        }

        composable<ContenidoPan>{ backStackEntry->
            val args =backStackEntry.toRoute<ContenidoPan>()
            ContenidoPantalla(navController = navController,
                contenidoId = args.idContenido)
        }

        composable<CrearContenidoPan>{ backStackEntry ->
            val args = backStackEntry.toRoute<CrearContenidoPan>()
            EntradaContenidoPantalla(navController = navController, categoriaId = args.idCategoria)
        }

        composable<EditarContenidoPan> { backStackEntry ->
            val args = backStackEntry.toRoute<EditarContenidoPan>()
            ContenidoEditarPantalla(
                navController = navController,
                contenidoId = args.idContenido,
                onSaveSuccess ={navController.popBackStack()}
            )
        }
    }
}