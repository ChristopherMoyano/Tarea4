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

class EditarContenidoViewModel (
    private val repositorioContenido: RepositorioContenido,
    private val repositorioCategoria: RepositorioCategoria
): ViewModel(){

    private var Idcontenido: Int? = null

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
        viewModelScope.launch{
            repositorioCategoria.ObtenerTodasLasCategorias().collect{
                _categorias.value = it
            }
        }
    }

    fun CargarContenido(contenido: Contenido){
        Idcontenido = contenido.id
        _nombre.value = contenido.nombreContenido
        _informacion.value = contenido.info
        _imagen.value = contenido.imagenContenido

        val categoria = _categorias.value.find{it.nombre == contenido.nombreCategoria}
        _categoriaSeleccionada.value = categoria
    }

    fun CargarContenidoPorId(contenidoId: Int){
        viewModelScope.launch {
            val contenido = repositorioContenido.getContentById(contenidoId)
            contenido?.let{ CargarContenido(it)}
        }
    }

    fun cambiarNombre(value: String){_nombre.value = value}
    fun cambiarInformacion(value: String){_informacion.value = value}
    fun cambiarImagen(value: String){_imagen.value = value}
    fun cambiarCategoria(categoria: Categoria){_categoriaSeleccionada.value = categoria}

    fun actualizarContenido(){
        viewModelScope.launch {
            val nombreCategoriaSeleccionada = _categoriaSeleccionada.value?.nombre ?: return@launch

            val actualizarContenido = Contenido(
                id = Idcontenido ?:return@launch,
                nombreContenido = nombre.value,
                info = informacion.value,
                nombreCategoria = nombreCategoriaSeleccionada,
                imagenContenido = imagen.value
            )
            repositorioContenido.updateContent(actualizarContenido)
        }
    }
}