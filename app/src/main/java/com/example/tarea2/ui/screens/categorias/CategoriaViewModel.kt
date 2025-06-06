package com.example.tarea2.ui.screens.categorias


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea2.data.model.Categoria
import com.example.tarea2.data.remote.services.CategoriaService
import kotlinx.coroutines.launch

sealed class CategoriasUiState{
    data class Success(val categorias: List<Categoria>): CategoriasUiState()
    data class Error(val message: String): CategoriasUiState()
    object Loading: CategoriasUiState()
    object Idle: CategoriasUiState()
}

class CategoriaViewModel(
    private val categoriaService: CategoriaService
): ViewModel(){
    var categoriasUiState: CategoriasUiState by mutableStateOf(CategoriasUiState.Loading)
        private set

    fun resetUiState(){
        categoriasUiState = CategoriasUiState.Idle
    }

    private fun getCategorias(){
        viewModelScope.launch {
            categoriasUiState = CategoriasUiState.Loading
            categoriasUiState = try {
                val listCategorias = categoriaService.getCategorias()
                CategoriasUiState.Success(listCategorias)
            } catch (e: Exception){
                CategoriasUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun refreshCategorias(){
        getCategorias()
    }

    fun addCategoria(nombre: String, imagen: String?){
        viewModelScope.launch {
            categoriasUiState = CategoriasUiState.Loading
            try {
                categoriaService.addCategoria(Categoria(0,nombre,imagen))
                val updateCategoria = categoriaService.getCategorias()
                categoriasUiState = CategoriasUiState.Success(updateCategoria)
            }catch (e: Exception){
                categoriasUiState = CategoriasUiState.Error(e.message ?: "Error al agregar Categoria")
            }
        }
    }

    fun deleteCategoria(id: Int) {
        viewModelScope.launch {
            categoriasUiState = CategoriasUiState.Loading
            try {
                categoriaService.deleteCategoria(id)
                val updatedList = categoriaService.getCategorias()
                categoriasUiState = CategoriasUiState.Success(updatedList)
            } catch (e: Exception) {
                categoriasUiState = CategoriasUiState.Error(e.message ?: "Error al eliminar la categoría")
            }
        }
    }

    fun updateCategoria(id: Int, nombre: String, imagen: String?) {
        viewModelScope.launch {
            categoriasUiState = CategoriasUiState.Loading
            try {
                val categoriaActualizada = Categoria(id, nombre, imagen)
                categoriaService.updateCategoria(id, categoriaActualizada)
                val updatedList = categoriaService.getCategorias()
                categoriasUiState = CategoriasUiState.Success(updatedList)
            } catch (e: Exception) {
                categoriasUiState = CategoriasUiState.Error(e.message ?: "Error al actualizar la categoría")
            }
        }
    }
}