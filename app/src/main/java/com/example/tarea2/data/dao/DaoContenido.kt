package com.example.tarea2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tarea2.data.entity.Contenido
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoContenido {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contenido: Contenido)

    @Update
    suspend fun update(contenido: Contenido)

    @Delete
    suspend fun delete(contenido: Contenido)

    @Query("SELECT * FROM contenido WHERE nombreCategoria = :nombreCategoria")
    fun getByCategoria(nombreCategoria: String): Flow<List<Contenido>>

    @Query("SELECT * FROM contenido WHERE id = :id")
    fun getById(id:Int): Flow<Contenido?>

    @Query("SELECT * FROM contenido WHERE  id = :id")
    fun getContenidoById(id: Int):Contenido?
}