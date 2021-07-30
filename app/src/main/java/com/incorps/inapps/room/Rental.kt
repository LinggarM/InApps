package com.incorps.inapps.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_rental")
data class Rental(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "user")
    var user: String?,
    @ColumnInfo(name = "product")
    var product: Int,
    @ColumnInfo(name = "url_identitas")
    var url_identitas: String?,
    @ColumnInfo(name = "tgl_peminjaman")
    var tgl_peminjaman: Long,
    @ColumnInfo(name = "tgl_pengembalian")
    var tgl_pengembalian: Long,
    @ColumnInfo(name = "lama_peminjaman")
    var lama_peminjaman: Int,
    @ColumnInfo(name = "quantity")
    var quantity: Int,
    @ColumnInfo(name = "organisasi")
    var organisasi: String?,
    @ColumnInfo(name = "antar")
    var antar: Boolean,
    @ColumnInfo(name = "address")
    var address: String?,
    @ColumnInfo(name = "price")
    var price: Int,
)