package com.example.tarea2.data.remote.services

import com.example.tarea2.data.model.Contenido // Asegúrate de que la importación sea correcta
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContenidoService {

    @GET("api/categorias/{categoriaId}/contenidos")
    suspend fun getContenidosDeCategoria(@Path("categoriaId") categoriaId: Int): List<Contenido>


    @POST("api/categorias/{categoriaId}/contenidos")
    suspend fun addContenidoEnCategoria(@Path("categoriaId") categoriaId: Int, @Body contenido: Contenido): Contenido

    @PUT("api/contenidos/{contenidoId}")
    suspend fun updateContenido(@Path("contenidoId") contenidoId: Int, @Body contenido: Contenido): Contenido

    @DELETE("api/contenidos/{contenidoId}")
    suspend fun deleteContenido(@Path("contenidoId") contenidoId: Int): Unit

    @GET("api/contenidos/{id}")
    suspend fun getContenidoById(@Path("id") id: Int): Contenido
}