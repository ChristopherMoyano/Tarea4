package com.example.tarea2.ui.screens.contenidos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea2.data.model.Contenido
import com.example.tarea2.data.remote.services.ContenidoService
import kotlinx.coroutines.launch
import java.io.IOException

// Estados de la UI para la pantalla de detalle
sealed interface ContenidoDetailUiState {
    data class Success(val contenido: Contenido) : ContenidoDetailUiState
    data class Error(val message: String) : ContenidoDetailUiState
    object Loading : ContenidoDetailUiState
    object DeleteSuccess : ContenidoDetailUiState // Estado para señalar borrado exitoso
}

class ContenidoDetailViewModel(
    private val contenidoService: ContenidoService
) : ViewModel() {

    var uiState: ContenidoDetailUiState by mutableStateOf(ContenidoDetailUiState.Loading)
        private set

    // Carga los detalles de un contenido específico
    fun loadContenido(contenidoId: Int) {
        viewModelScope.launch {
            uiState = ContenidoDetailUiState.Loading
            uiState = try {
                val contenido = contenidoService.getContenidoById(contenidoId)
                ContenidoDetailUiState.Success(contenido)
            } catch (e: IOException) {
                ContenidoDetailUiState.Error("No se pudieron cargar los detalles.")
            } catch (e: Exception) {
                ContenidoDetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    // Borra el contenido actual
    fun deleteContenido(contenidoId: Int) {
        viewModelScope.launch {
            // No cambiamos a Loading para que la UI no parpadee.
            // La UI puede mostrar un diálogo o deshabilitar botones.
            try {
                contenidoService.deleteContenido(contenidoId)
                uiState = ContenidoDetailUiState.DeleteSuccess
            } catch (e: IOException) {
                uiState = ContenidoDetailUiState.Error("No se pudo eliminar el contenido.")
            } catch (e: Exception) {
                uiState = ContenidoDetailUiState.Error(e.message ?: "Error al eliminar")
            }
        }
    }
}