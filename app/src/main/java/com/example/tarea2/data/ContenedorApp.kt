package com.example.tarea2.data

import android.content.Context
import com.example.tarea2.data.offline.CategoriaOfflineRepositorio
import com.example.tarea2.data.offline.ContenidoOfflineRepositorio
import com.example.tarea2.data.repositorio.RepositorioCategoria
import com.example.tarea2.data.repositorio.RepositorioContenido

class ContenedorApp(private val context: Context) {

    val repositorioCategoria: RepositorioCategoria by lazy {
        CategoriaOfflineRepositorio(BasedeDatosApp.getBaseDeDatos(context).categoriaDao())
    }

    val repositorioContenido: RepositorioContenido by lazy{
        ContenidoOfflineRepositorio(BasedeDatosApp.getBaseDeDatos(context).contenidoDao())
    }
}