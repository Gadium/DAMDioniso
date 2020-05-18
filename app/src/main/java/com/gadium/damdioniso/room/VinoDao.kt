package com.gadium.damdioniso.room

import androidx.room.*

/**
 * Interface con los métodos que usaremos para conectar el código de la aplicación con la base de datos
 */
@Dao
interface VinoDao {

    /**
     * Función para añadir un vino
     */
    @Insert
    suspend fun addVino(vino: Vino)

    /**
     * Función para seleccionar todos los vinos ordenados por su ID
     */
    @Query("SELECT * FROM vino ORDER BY id DESC")
    suspend fun getAllVinos(): List<Vino>

    /**
     * Función para seleccionar los vinos marcados como favoritos
     */
    @Query("SELECT * FROM vino WHERE favorito LIKE 1")
    suspend fun getAllVinosFav(): List<Vino>

    /**
     * Función para añadir múltiples vinos.
     * No usado en la versión 1
     */
    @Insert
    suspend fun addMultipleVinos(vararg vino: Vino)

    /**
     * Función para actualizar un vino
     */
    @Update
    suspend fun updateVino(vino: Vino)

    /***
     * Función para borrar un vino
     */
    @Delete
    suspend fun deleteVino(vino: Vino)
}