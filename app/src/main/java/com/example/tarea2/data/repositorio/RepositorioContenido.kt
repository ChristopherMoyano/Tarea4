package com.example.tarea2.data.repositorio

import com.example.tarea2.data.entity.Contenido
import kotlinx.coroutines.flow.Flow


interface RepositorioContenido {

    fun getContentsByCategoryStream(categoryName: String): Flow<List<Contenido>>

    fun getContentStream(id: Int): Flow<Contenido?>

    suspend fun insertContent(content: Contenido)

    suspend fun deleteContent(content: Contenido)

    suspend fun updateContent(content: Contenido)

    suspend fun getContentById(id: Int): Contenido?
}