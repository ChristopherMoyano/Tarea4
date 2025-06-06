package com.example.tarea2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Contenido (
    val id: Int,
    val nombreContenido: String,
    val informacion: String,
    val idCategoria: Int,
    val imagenContenido: String?
)