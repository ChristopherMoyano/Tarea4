package com.example.tarea2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "contenido",
    foreignKeys =[ForeignKey(
        entity =Categoria::class,
        parentColumns =["nombre"],
        childColumns = ["nombreCategoria"],
        onDelete = ForeignKey.CASCADE
    )],
    indices =[Index(value = ["nombreCategoria"])]
)
data class Contenido(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nombreContenido") val nombreContenido:String,
    @ColumnInfo(name = "Informacion") val info:String,
    @ColumnInfo(name = "nombreCategoria") val nombreCategoria:String,
    @ColumnInfo(name = "imagenContenido") val imagenContenido:String?
)