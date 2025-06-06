package com.example.tarea2.ui.vistas.contenido

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea2.data.entity.Contenido
import com.example.tarea2.data.repositorio.RepositorioContenido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContenidoPantallaViewModel(
    private val repositorioContenido: RepositorioContenido
) : ViewModel()  {

    private val _contenido = MutableStateFlow<Contenido?>(null)
    val contenido: StateFlow<Contenido?> = _contenido

    fun CargarCotenido(contenidoId: Int){
        viewModelScope.launch {
            repositorioContenido.getContentStream(contenidoId).collect{ cargado->
                _contenido.value = cargado
            }
        }
    }

    fun BorrarContenido(){
        viewModelScope.launch {
            _contenido.value?.let{
                repositorioContenido.deleteContent(it)
            }
        }
    }
}