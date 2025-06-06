package com.example.tarea2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tarea2.data.entity.Categoria
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoCategoria {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoria: Categoria)

    @Update
    suspend fun update(categoria: Categoria)

    @Delete
    suspend fun delete(categoria: Categoria)

    @Query("SELECT * FROM categoria")
    fun getAll():Flow<List<Categoria>>

    @Query("SELECT * FROM categoria WHERE id = :id")
    fun getById(id: Int): Flow<Categoria>
}