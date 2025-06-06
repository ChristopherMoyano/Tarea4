package com.example.tarea2.ui.vistas.categorias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea2.data.entity.Categoria
import com.example.tarea2.data.repositorio.RepositorioCategoria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

class EntradaCategoriaViewModel(
    private val repositorio: RepositorioCategoria
) : ViewModel() {


    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _imagenUri = MutableStateFlow<String?>(null)
    val imagenUri: StateFlow<String?> = _imagenUri

    val categorias: StateFlow<List<Categoria>> = repositorio.ObtenerTodasLasCategorias()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _mensajeError = MutableStateFlow("")
    val mensajeError: StateFlow<String> = _mensajeError


    fun cambiarNombre(nuevoNombre: String) {
        _nombre.value = nuevoNombre
    }

    fun cambiarImagen(nuevaImagen: String?) {
        _imagenUri.value = nuevaImagen
    }


    fun guardarCategoria() {
        viewModelScope.launch {
            val nameTrimmed = nombre.value.trim()


            val existente = repositorio.ObtenerTodasLasCategorias().first()

            // Verificar duplicados
            if (existente.any { it.nombre.equals(nameTrimmed, ignoreCase = true) }) {
                _mensajeError.value = "Ya existe una categoría con ese nombre"
                return@launch
            }

            // Si no hay duplicado, guardar
            val categoria = Categoria(
                nombre = nameTrimmed,
                imagenCategoria = imagenUri.value
            )
            repositorio.insertarCategoria(categoria)

            // Limpiar campos y mensaje
            _nombre.value = ""
            _imagenUri.value = null
            _mensajeError.value = ""
        }
    }

}