package com.gadium.damdioniso.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            VinoDatabase::class.java,
            "vinodatabase"
        ).build()
    }
    
}