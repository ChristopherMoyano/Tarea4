package com.example.tarea2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categoria",
    indices = [Index(value = ["nombre"],unique = true)]
)
data class Categoria(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "nombre") val nombre : String,

    @ColumnInfo(name = "imagen_categoria") val imagenCategoria: String?
)