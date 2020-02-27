package com.gadium.damdioniso.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Vino(
    val nombre: String?,
    val bodega: String?,
    val crianza: String?
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(bodega)
        parcel.writeString(crianza)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vino> {
        override fun createFromParcel(parcel: Parcel): Vino {
            return Vino(parcel)
        }

        override fun newArray(size: Int): Array<Vino?> {
            return arrayOfNulls(size)
        }
    }
}