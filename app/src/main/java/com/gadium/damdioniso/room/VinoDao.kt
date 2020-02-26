package com.gadium.damdioniso.room

import androidx.room.*

@Dao
interface VinoDao {

    @Insert
    suspend fun addVino(vino: Vino)

    @Query("SELECT * FROM vino ORDER BY id DESC")
    suspend fun getAllVinos(): List<Vino>

    @Query("SELECT * FROM vino WHERE favorito LIKE 1")
    suspend fun getAllVinosFav(): List<Vino>

    @Insert
    suspend fun addMultipleVinos(vararg vino: Vino)

    @Update
    suspend fun updateVino(vino: Vino)

    @Delete
    suspend fun deleteVino(vino: Vino)
}