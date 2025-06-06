package com.example.tarea2.data.repositorio

import com.example.tarea2.data.entity.Categoria
import kotlinx.coroutines.flow.Flow

interface RepositorioCategoria {

    fun ObtenerTodasLasCategorias(): Flow<List<Categoria>>

    fun ObtenerCategoria(id: Int): Flow<Categoria?>

    suspend fun insertarCategoria(categoria: Categoria)

    suspend fun BorrarCategoria(categoria: Categoria)

    suspend fun updateCategoria(categoria: Categoria)
}