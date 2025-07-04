package com.example.tarea2.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tarea2.AuthManager
import com.example.tarea2.Categorias
import com.example.tarea2.Login
import com.example.tarea2.ui.screens.auth.AuthUiState
import com.example.tarea2.ui.screens.auth.AuthViewModel

@Composable
fun AuthManager(viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory),
                navController: NavHostController
) {
    val authState = viewModel.authState

    LaunchedEffect(authState) {
        when (authState) {
            is AuthUiState.loggedIn -> {
                if (authState.logged) {
                    navController.navigate(Categorias) {
                        popUpTo(AuthManager) {
                            inclusive = true
                        } // Quitar la pantalla de la pila de navegación
                    }
                }
                if (!authState.logged) {
                    navController.navigate(Login) {
                        popUpTo(AuthManager) {
                            inclusive = true
                        } // Quitar la pantalla de la pila de navegación
                    }
                }
            }
            else -> {}
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}