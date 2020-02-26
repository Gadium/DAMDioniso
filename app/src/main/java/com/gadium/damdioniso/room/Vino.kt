package com.gadium.damdioniso.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Vino (
    val nombre: String,
    val bodega: String,
    val crianza: String,
    val favorito: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}