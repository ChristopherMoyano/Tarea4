package com.example.tarea2.data.offline

import com.example.tarea2.data.dao.DaoContenido
import com.example.tarea2.data.entity.Contenido
import com.example.tarea2.data.repositorio.RepositorioContenido
import kotlinx.coroutines.flow.Flow

class ContenidoOfflineRepositorio(private val contenidoDao: DaoContenido): RepositorioContenido {

    override fun getContentsByCategoryStream(nombreCategoria: String): Flow<List<Contenido>> =
        contenidoDao.getByCategoria(nombreCategoria)

    override fun getContentStream(id: Int): Flow<Contenido?> = contenidoDao.getById(id)

    override suspend fun insertContent(contenido: Contenido) = contenidoDao.insert(contenido)

    override suspend fun deleteContent(contenido: Contenido) = contenidoDao.delete(contenido)

    override suspend fun updateContent(contenido: Contenido) = contenidoDao.update(contenido)

    override suspend fun getContentById(id: Int): Contenido? = contenidoDao.getContenidoById(id)

}