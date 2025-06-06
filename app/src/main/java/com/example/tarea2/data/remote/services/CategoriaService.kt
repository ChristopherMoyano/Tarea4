package com.example.tarea2.data.remote.services

import com.example.tarea2.data.model.Categoria
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoriaService {

    @GET("api/categorias")
    suspend fun getCategorias():List<Categoria>

    @POST("api/categorias")
    suspend fun addCategoria(@Body categoria: Categoria): Categoria

    @DELETE("api/categorias/{id}")
    suspend fun deleteCategoria(@Path("id")id: Int):Unit

    @PUT("api/categorias/{id}")
    suspend fun updateCategoria(@Path("id")id: Int,@Body categoria: Categoria): Categoria

}