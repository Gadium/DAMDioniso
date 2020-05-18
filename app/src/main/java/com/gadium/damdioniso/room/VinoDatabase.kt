package com.gadium.damdioniso.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Clase que gestiona la creación e instanciación de la base de datos
 */
@Database(
    entities = arrayOf(Vino::class),
    version = 1
)
abstract class VinoDatabase : RoomDatabase() {
    abstract fun getVinoDao(): VinoDao

    companion object {
        @Volatile
        private var instance: VinoDatabase? = null
        private val LOCK = Any()

        // Si la base de datos no está ya invocada la invocamos
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        /**
         * Función para construir la base de datos
         */
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            VinoDatabase::class.java,
            "vinodatabase"
        ).build()
    }

}