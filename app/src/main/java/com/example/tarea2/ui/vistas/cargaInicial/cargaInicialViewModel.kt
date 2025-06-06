package com.example.tarea2.ui.vistas.cargaInicial

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea2.R
import com.example.tarea2.data.entity.Categoria
import com.example.tarea2.data.entity.Contenido
import com.example.tarea2.data.repositorio.RepositorioCategoria
import com.example.tarea2.data.repositorio.RepositorioContenido
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class cargaInicialViewModel(
    private val repositorioContenido: RepositorioContenido,
    private val repositorioCategoria: RepositorioCategoria
):ViewModel() {
    fun cargaInicialDatos(context: Context) {
        viewModelScope.launch {
            val preferencia = context.getSharedPreferences(
                "AnimeList-preferencias",
                Context.MODE_PRIVATE
            )
            val yaCargado = preferencia.getBoolean("initial_data_loaded", false)
            if (yaCargado) return@launch

            val categoriasExistentes = repositorioCategoria.ObtenerTodasLasCategorias().first()
            val categoriaARevisar = listOf("Accion", "Romance", "Fantasia")
            val pkgC = context.packageName
            categoriaARevisar.forEach { nombre ->
                if (categoriasExistentes.none { it.nombre == nombre }) {
                    val imageId = when (nombre) {
                        "Accion" -> R.drawable.accion
                        "Romance" -> R.drawable.romance
                        "Fantasia" -> R.drawable.fantasia
                        else -> R.drawable.otros
                    }
                    repositorioCategoria.insertarCategoria(
                        Categoria(
                            nombre = nombre,
                            imagenCategoria = "android.resource://$pkgC/$imageId"
                        )
                    )
                    val contenidoExiste = repositorioContenido.getContentsByCategoryStream(nombre
                    ).first()
                    if(contenidoExiste.isEmpty()){
                        val pkg = context.packageName

                        val contenidos = when(nombre) {
                            "Accion" -> listOf(
                                Contenido(
                                    nombreContenido = "Naruto",
                                    imagenContenido ="android.resource://$pkg/${R.drawable.naruto}",
                                    nombreCategoria = nombre,
                                    info = context.getString(R.string.Naruto)
                                ),
                                Contenido(
                                    nombreContenido = "Bleach",
                                    imagenContenido = "android.resource://$pkg/${R.drawable.bleach}",
                                    nombreCategoria = nombre,
                                    info = context.getString(R.string.Bleach)
                                )
                            )
                            "Romance"-> listOf(
                                Contenido(
                                    nombreContenido = "Danjo no Yuujou wa Seiritsu suru?",
                                    imagenContenido = "android.resource://$pkg/${R.drawable.
                                    danjo_no_yuujou_wa_seiritsu_suru}",
                                    nombreCategoria = nombre,
                                    info = context.getString(R.string.Danjo_no_Yuujou_wa_Seiritsu_suru)
                                ),
                                Contenido(
                                    nombreContenido = "Class no Daikirai na Joshi to Kekkon " +
                                            "suru Koto ni Natta",
                                    imagenContenido = "android.resource://$pkg/" +
                                            "${R.drawable.
                                            class_no_daikirai_na_joshi_to_kekkon_suru}",
                                    nombreCategoria = nombre,
                                    info = context.getString(R.string.
                                    Class_no_Daikirai_na_Joshi_to_Kekkon_suru_Koto_ni_Natta)
                                )
                            )
                            "Fantasia"-> listOf(
                                Contenido(
                                    nombreContenido = "Girumasu",
                                    imagenContenido = "android.resource://$pkg/${R.
                                    drawable.girumasu}",
                                    nombreCategoria = nombre,
                                    info = context.getString(R.string.Girumasu)
                                ),
                                Contenido(
                                    nombreContenido = "Farmagia",
                                    imagenContenido = "android.resource://$pkg/${R.
                                    drawable.farmagia}",
                                    nombreCategoria = nombre,
                                    info = context.getString(R.string.Farmagia)
                                )
                            )
                            else -> emptyList()
                        }
                        contenidos.forEach{ repositorioContenido.insertContent(it)}
                    }
                }
            }
            preferencia.edit().putBoolean("initial_data_loaded",true).apply()
        }

    }
}