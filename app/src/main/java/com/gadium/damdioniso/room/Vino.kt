package com.gadium.damdioniso.room

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad Vino del modelo de datos
 */
@Entity
data class Vino(
    val nombre: String?,
    val bodega: String?,
    val denominacion: String?,
    val crianza: String?,
    val tipo: String?,
    val uvas: String?,
    val alcohol: String?,
    val envejecimiento: String?,
    val paisOrigen: String?,
    val precio: String?,
    val notasCata: String?,
    val favorito: Boolean?
): Parcelable { //el atributo id, Primary Key de nuestra tabla, lo sacamos del constructor porque se va a autogenerar al crearse
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readBoolean()
    ) {
        id = parcel.readInt()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(bodega)
        parcel.writeString(denominacion)
        parcel.writeString(crianza)
        parcel.writeString(tipo)
        parcel.writeString(uvas)
        parcel.writeString(alcohol)
        parcel.writeString(envejecimiento)
        parcel.writeString(paisOrigen)
        parcel.writeString(precio)
        parcel.writeString(notasCata)
        parcel.writeInt(id)
        if (favorito != null) {
            parcel.writeBoolean(favorito)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vino> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Vino {
            return Vino(parcel)
        }

        override fun newArray(size: Int): Array<Vino?> {
            return arrayOfNulls(size)
        }
    }
}