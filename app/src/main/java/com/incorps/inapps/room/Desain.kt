package com.incorps.inapps.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_desain")
data class Desain(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var user: String?,
    var product: Int,
    var deskripsi_desain: String?,
    var waktu_pengerjaan: Int,
    var url_file_pendukung: String?,
    var email_pengiriman: String?,
    var organisasi: String?,
    var price: Int,
)