package com.example.tarea2.data.offline

import com.example.tarea2.data.dao.DaoCategoria
import com.example.tarea2.data.entity.Categoria
import com.example.tarea2.data.repositorio.RepositorioCategoria
import kotlinx.coroutines.flow.Flow

class CategoriaOfflineRepositorio(private val categoriaDao: DaoCategoria): RepositorioCategoria {

    override fun ObtenerTodasLasCategorias(): Flow<List<Categoria>> = categoriaDao.getAll()

    override fun ObtenerCategoria(id: Int): Flow<Categoria?> =categoriaDao.getById(id)

    override suspend fun insertarCategoria(categoria: Categoria) = categoriaDao.insert(categoria)

    override suspend fun BorrarCategoria(categoria: Categoria) = categoriaDao.delete(categoria)

    override suspend fun updateCategoria(categoria: Categoria) =categoriaDao.update(categoria)

}