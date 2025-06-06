package com.example.tarea2.ui.screens.contenidos


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea2.data.model.Contenido // Asegúrate que la ruta al modelo sea correcta
import com.example.tarea2.data.remote.services.ContenidoService
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Define los posibles estados de la UI para la pantalla de edición de contenido.
 * Este estado es más específico que otros porque maneja un flujo de 2 pasos:
 * 1. Cargar los datos iniciales del contenido.
 * 2. Enviar la actualización de esos datos.
 */
sealed interface UpdateContenidoUiState {
    // Éxito al cargar los datos iniciales, el formulario se puede rellenar.
    data class Success(val contenido: Contenido) : UpdateContenidoUiState
    // Error en cualquier momento (cargando o actualizando).
    data class Error(val message: String) : UpdateContenidoUiState
    // Cargando los datos iniciales del contenido.
    object LoadingInitialData : UpdateContenidoUiState
    // La actualización se está enviando a la API.
    object UpdateInProgress : UpdateContenidoUiState
    // La actualización fue exitosa, usado para disparar la navegación hacia atrás.
    object UpdateSuccess : UpdateContenidoUiState
}

class UpdateContenidoViewModel(
    private val contenidoService: ContenidoService
) : ViewModel() {

    // Variable de estado que la UI observará.
    var uiState: UpdateContenidoUiState by mutableStateOf(UpdateContenidoUiState.LoadingInitialData)
        private set

    /**
     * Carga un contenido específico por su ID desde la API.
     * Se debe llamar al entrar a la pantalla de edición.
     */
    fun loadContenido(contenidoId: Int) {
        viewModelScope.launch {
            uiState = UpdateContenidoUiState.LoadingInitialData
            uiState = try {
                // Llama a la función del servicio que obtiene un solo contenido.
                val contenido = contenidoService.getContenidoById(contenidoId)
                UpdateContenidoUiState.Success(contenido)
            } catch (e: IOException) {
                UpdateContenidoUiState.Error("No se pudo cargar el contenido. Verifique su conexión.")
            } catch (e: Exception) {
                UpdateContenidoUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    /**
     * Envía los datos actualizados del contenido a la API.
     */
    fun updateContenido(
        contenidoId: Int,
        nombre: String,
        informacion: String,
        imagen: String?,
        categoriaId: Int
    ) {
        viewModelScope.launch {
            uiState = UpdateContenidoUiState.UpdateInProgress
            try {
                // Crea el objeto Contenido con los datos del formulario.
                val contenidoActualizado = Contenido(
                    id = contenidoId,
                    nombreContenido = nombre,
                    informacion = informacion,
                    idCategoria = categoriaId,
                    imagenContenido = imagen
                )
                // Llama a la función del servicio para actualizar.
                contenidoService.updateContenido(contenidoId, contenidoActualizado)
                uiState = UpdateContenidoUiState.UpdateSuccess
            } catch (e: IOException) {
                uiState = UpdateContenidoUiState.Error("No se pudo actualizar. Verifique su conexión.")
            } catch (e: Exception) {
                uiState = UpdateContenidoUiState.Error(e.message ?: "Error desconocido al actualizar")
            }
        }
    }
}