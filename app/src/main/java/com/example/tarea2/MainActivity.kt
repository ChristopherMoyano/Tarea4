package com.example.tarea2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tarea2.ui.theme.Tarea2Theme
import com.example.tarea2.ui.vistas.AppViewModel
import com.example.tarea2.ui.vistas.cargaInicial.cargaInicialViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tarea2Theme {
                val viewModel:cargaInicialViewModel = viewModel(factory = AppViewModel.Factory)

                LaunchedEffect(Unit) {
                    viewModel.cargaInicialDatos(context = this@MainActivity)
                }
                Navigation()
            }
        }
    }
}