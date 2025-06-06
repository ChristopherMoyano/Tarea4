package com.example.tarea2

import android.app.Application
import com.example.tarea2.data.AppContainer
import com.example.tarea2.data.AppDataContainer

class Tarea_2: Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}