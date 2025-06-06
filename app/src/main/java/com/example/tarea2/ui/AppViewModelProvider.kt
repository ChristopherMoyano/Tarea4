package com.example.tarea2.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tarea2.Tarea_2
import com.example.tarea2.ui.screens.auth.AuthViewModel
import com.example.tarea2.ui.screens.categorias.CategoriaViewModel
import com.example.tarea2.ui.screens.contenidos.ContenidoViewModel
import com.example.tarea2.ui.screens.contenidos.UpdateContenidoViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            CategoriaViewModel(
                categoriaService = App().container.categoriaApiService
            )
        }
        initializer {
            AuthViewModel(
                authService = App().container.authApiService,
                context = App().baseContext
            )
        }
        initializer {
            ContenidoViewModel(
                contenidoService = App().container.contenidoApiService
            )
        }
        initializer {
            UpdateContenidoViewModel(
                contenidoService = App().container.contenidoApiService
            )
        }
    }
}

fun CreationExtras.App(): Tarea_2 =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Tarea_2)