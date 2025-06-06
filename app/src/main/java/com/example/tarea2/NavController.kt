package com.example.tarea2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tarea2.ui.AuthManager
import com.example.tarea2.ui.screens.auth.LoginScreen
import com.example.tarea2.ui.screens.categorias.CategoriasScreen
import com.example.tarea2.ui.screens.categorias.AddCategoriaScreen
import com.example.tarea2.ui.screens.contenidos.ContenidoScreen
import kotlinx.serialization.Serializable
import com.example.tarea2.ui.screens.contenidos.AddContenidoScreen
import com.example.tarea2.ui.screens.contenidos.ContenidoDetailScreen
import com.example.tarea2.ui.screens.contenidos.UpdateContenidoScreen

@Serializable
object Login

@Serializable
object AuthManager

@Serializable
object Categorias

@Serializable
object AddCategoria

@Serializable
data class ContenidosRoute(
    val categoriaId: Int,
    val nombreCategoria: String
)

@Serializable
data class AddContenido(val categoriaId: Int)

@Serializable
data class UpdateContenido(val contenidoId: Int)

@Serializable
data class ContenidoDetail(val contenidoId: Int)


@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AuthManager){
        composable<AuthManager>{
            AuthManager(navController = navController)
        }
        composable<Login>{
            LoginScreen(navController = navController)
        }
        composable<Categorias> {
            CategoriasScreen(navController = navController)
        }
        composable<AddCategoria> {
            AddCategoriaScreen(navController = navController)
        }
        composable<ContenidosRoute>{ backStackEntry ->
            // Extraemos los argumentos de la ruta de forma segura
            val args = backStackEntry.toRoute<ContenidosRoute>()

            // Llamamos a ContenidoScreen con los argumentos extraídos
            ContenidoScreen(
                navController = navController,
                categoriaId = args.categoriaId,
                nombreCategoria = args.nombreCategoria
            )
        }
        composable<AddContenido> { backStackEntry ->
            val args = backStackEntry.toRoute<AddContenido>()
            AddContenidoScreen(
                navController = navController,
                categoriaId = args.categoriaId
            )
        }
        composable<UpdateContenido> { backStackEntry ->
            val args = backStackEntry.toRoute<UpdateContenido>()
            UpdateContenidoScreen(
                navController = navController,
                contenidoId = args.contenidoId
            )
        }
        composable<ContenidoDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<ContenidoDetail>()
            ContenidoDetailScreen(
                navController = navController,
                contenidoId = args.contenidoId
            )
        }


    }
}