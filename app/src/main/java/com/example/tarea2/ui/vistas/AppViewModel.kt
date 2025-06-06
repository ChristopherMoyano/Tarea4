package com.example.tarea2.ui.vistas

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tarea2.Tarea2App
import com.example.tarea2.ui.vistas.cargaInicial.cargaInicialViewModel
import com.example.tarea2.ui.vistas.categorias.DentroCategoriaViewModel
import com.example.tarea2.ui.vistas.categorias.EntradaCategoriaViewModel
import com.example.tarea2.ui.vistas.contenido.ContenidoPantalla
import com.example.tarea2.ui.vistas.contenido.ContenidoPantallaViewModel
import com.example.tarea2.ui.vistas.contenido.EditarContenidoViewModel
import com.example.tarea2.ui.vistas.contenido.EntradaContenidoViewModel

object AppViewModel{
    val Factory = viewModelFactory {

        initializer {
            cargaInicialViewModel(
                Tarea2App().contenedor.repositorioContenido,
                Tarea2App().contenedor.repositorioCategoria
            )
        }

        initializer {
            DentroCategoriaViewModel(
                Tarea2App().contenedor.repositorioContenido
            )
        }

        initializer {
            EntradaCategoriaViewModel(
                Tarea2App().contenedor.repositorioCategoria
            )
        }

        initializer {
            ContenidoPantallaViewModel(
                Tarea2App().contenedor.repositorioContenido
            )
        }

        initializer {
            EditarContenidoViewModel(
                Tarea2App().contenedor.repositorioContenido,
                Tarea2App().contenedor.repositorioCategoria
            )
        }

        initializer {
            EntradaContenidoViewModel(
                Tarea2App().contenedor.repositorioCategoria,
                Tarea2App().contenedor.repositorioContenido
            )
        }
    }
}

fun CreationExtras.Tarea2App():Tarea2App = (this[APPLICATION_KEY] as Tarea2App)