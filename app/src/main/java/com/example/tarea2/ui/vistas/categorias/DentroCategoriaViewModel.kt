package com.example.tarea2.ui.vistas.categorias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea2.data.entity.Contenido
import com.example.tarea2.data.repositorio.RepositorioContenido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DentroCategoriaViewModel(
    private val repositorioContenido: RepositorioContenido
): ViewModel() {

    private val _contenidos = MutableStateFlow<List<Contenido>>(emptyList())
    val contenidos: StateFlow<List<Contenido>> =_contenidos


    fun cargaContenidosPorCategoria(nombreCategoria: String){
        viewModelScope.launch {
            repositorioContenido.getContentsByCategoryStream(nombreCategoria).collect{
                list -> _contenidos.value = list
            }
        }
    }
}