package com.example.tarea2.ui.screens.contenidos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea2.data.model.Contenido
import com.example.tarea2.data.remote.services.ContenidoService
import kotlinx.coroutines.launch

// Estado de la UI para la pantalla de contenidos
sealed class ContenidoUiState {
    data class Success(val contenidos: List<Contenido>) : ContenidoUiState()
    data class Error(val message: String) : ContenidoUiState()
    object Loading : ContenidoUiState()
    object Idle : ContenidoUiState()
}

class ContenidoViewModel(
    private val contenidoService: ContenidoService
) : ViewModel() {

    var contenidoUiState: ContenidoUiState by mutableStateOf(ContenidoUiState.Loading)
        private set

    fun resetUiState() {
        contenidoUiState = ContenidoUiState.Idle
    }

    // Obtiene los contenidos para una categoría específica por su ID
    fun getContenidos(categoriaId: Int) {
        viewModelScope.launch {
            contenidoUiState = ContenidoUiState.Loading
            contenidoUiState = try {
                val listaContenidos = contenidoService.getContenidosDeCategoria(categoriaId)
                ContenidoUiState.Success(listaContenidos)
            } catch (e: Exception) {
                ContenidoUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    // Elimina un contenido y refresca la lista
    fun deleteContenido(contenidoId: Int, categoriaId: Int) {
        viewModelScope.launch {
            contenidoUiState = ContenidoUiState.Loading
            try {
                contenidoService.deleteContenido(contenidoId)
                // Después de borrar, refrescamos la lista para que la UI se actualice
                val listaContenidos = contenidoService.getContenidosDeCategoria(categoriaId)
                contenidoUiState = ContenidoUiState.Success(listaContenidos)
            } catch (e: Exception) {
                contenidoUiState = ContenidoUiState.Error(e.message ?: "Error al eliminar el contenido")
            }
        }
    }

    fun addContenido(
        nombre: String,
        informacion: String,
        imagen: String?,
        categoriaId: Int
    ) {
        viewModelScope.launch {
            contenidoUiState = ContenidoUiState.Loading
            try {
                // El ID se pone en 0 porque la API debería generarlo.
                // El nombre de la categoría no es necesario si usas el idCategoria.
                val nuevoContenido = Contenido(0, nombre, informacion, categoriaId, imagen)
                contenidoService.addContenidoEnCategoria(categoriaId, nuevoContenido)
                // Cambiamos el estado a Success sin datos, la navegación se encargará del resto.
                contenidoUiState = ContenidoUiState.Success(emptyList()) // Señal de éxito
            } catch (e: Exception) {
                contenidoUiState = ContenidoUiState.Error(e.message ?: "Error al añadir el contenido")
            }
        }
    }
}