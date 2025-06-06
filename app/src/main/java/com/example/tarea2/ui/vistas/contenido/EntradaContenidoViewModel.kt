package com.example.tarea2.ui.vistas.contenido

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea2.data.entity.Categoria
import com.example.tarea2.data.entity.Contenido
import com.example.tarea2.data.repositorio.RepositorioCategoria
import com.example.tarea2.data.repositorio.RepositorioContenido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EntradaContenidoViewModel(
    private val repositorioCategoria: RepositorioCategoria,
    private val repositorioContenido: RepositorioContenido
) : ViewModel() {

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _informacion = MutableStateFlow("")
    val informacion: StateFlow<String> = _informacion

    private val _imagen = MutableStateFlow<String?>(null)
    val imagen: StateFlow<String?> = _imagen

    private val _categorias = MutableStateFlow<List<Categoria>>(emptyList())
    val categorias: StateFlow<List<Categoria>> = _categorias

    private val _categoriaSeleccionada = MutableStateFlow<Categoria?>(null)
    val categoriaSeleccionada: StateFlow<Categoria?> = _categoriaSeleccionada

    init{
        viewModelScope.launch {
            repositorioCategoria.ObtenerTodasLasCategorias().collect{ listaCategorias ->
                _categorias.value = listaCategorias
            }
        }
    }

    fun enCategoriaSeleccionada(categoria: Categoria){
        _categoriaSeleccionada.value = categoria
    }
    fun enCambiarNombre(value: String){
        _nombre.value = value
    }
    fun enCambiarInformacion(value: String){
        _informacion.value = value
    }
    fun enCambiarImagen(value: String){
        _imagen.value = value
    }

    fun guardarContenido(nombreCategoria : String){
        viewModelScope.launch {
            val nuevoContenido = Contenido(
                nombreContenido = nombre.value,
                info = informacion.value,
                nombreCategoria = nombreCategoria,
                imagenContenido = imagen.value
            )

            repositorioContenido.insertContent(nuevoContenido)
        }
    }
}