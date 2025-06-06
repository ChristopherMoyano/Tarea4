package com.example.tarea2

import android.app.Application
import com.example.tarea2.data.ContenedorApp

class Tarea2App: Application() {
    lateinit var contenedor: ContenedorApp
        private set

    override fun onCreate() {
        super.onCreate()
        contenedor = ContenedorApp(this)
    }
}