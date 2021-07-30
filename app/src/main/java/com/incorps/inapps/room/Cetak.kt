package com.incorps.inapps.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_cetak")
data class Cetak(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var user: String?,
    var product: Int,
    var url_file_desain: String?,
    var quantity: Int,
    var organisasi: String?,
    var price: Int,
)