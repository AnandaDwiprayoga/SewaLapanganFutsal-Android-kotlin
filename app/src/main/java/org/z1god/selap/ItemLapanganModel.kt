package org.z1god.selap

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemLapanganModel(
    val rating: Float,
    val nama : String,
    val alamat : String,
    val harga : String,
    val image: Int,
    val jumlahLap : Int
) : Parcelable