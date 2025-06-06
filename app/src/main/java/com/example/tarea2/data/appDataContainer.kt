package com.example.tarea2.data

import android.content.Context
import com.example.tarea2.data.remote.ApiClient
import com.example.tarea2.data.remote.services.AuthService
import com.example.tarea2.data.remote.services.CategoriaService
import com.example.tarea2.data.remote.services.ContenidoService

interface AppContainer{
    val authApiService : AuthService
    val categoriaApiService: CategoriaService
    val contenidoApiService: ContenidoService
}

class AppDataContainer(private val context: Context): AppContainer{
    override val authApiService: AuthService by lazy {
        ApiClient.create(context).create(AuthService::class.java)
    }

    override val categoriaApiService: CategoriaService by lazy {
        ApiClient.create(context).create(CategoriaService::class.java)
    }

    override val contenidoApiService: ContenidoService by lazy {
        ApiClient.create(context).create(ContenidoService::class.java)
    }
}