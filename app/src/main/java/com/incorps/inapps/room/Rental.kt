package com.incorps.inapps.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_rental")
data class Rental(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var user: String?,
    var product: Int,
    var url_identitas: String?,
    var tgl_peminjaman: Long,
    var tgl_pengembalian: Long,
    var lama_peminjaman: Int,
    var quantity: Int,
    var organisasi: String?,
    var antar: Boolean,
    var address: String?,
    var price: Int,
)