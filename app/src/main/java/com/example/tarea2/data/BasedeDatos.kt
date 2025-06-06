package com.example.tarea2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tarea2.data.dao.DaoCategoria
import com.example.tarea2.data.dao.DaoContenido
import com.example.tarea2.data.entity.Categoria
import com.example.tarea2.data.entity.Contenido


@Database(entities = [Categoria:: class, Contenido:: class], version = 1, exportSchema = false)
abstract class BasedeDatosApp : RoomDatabase(){

    abstract fun categoriaDao(): DaoCategoria
    abstract fun contenidoDao(): DaoContenido

    companion object{
        @Volatile
        private var Instance: BasedeDatosApp? = null

        fun getBaseDeDatos(context: Context):BasedeDatosApp{
            return  Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    BasedeDatosApp:: class.java,
                    "Base_de_datos_app"
                ).build().also{ Instance = it}
            }
        }
    }
}