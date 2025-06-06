package com.example.tarea2.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Categoria(
    val id: Int,
    val nombre: String,
    val imagen: String?
)