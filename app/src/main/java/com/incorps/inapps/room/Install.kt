package com.incorps.inapps.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_install")
data class Install(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var user: String?,
    var product: Int,
    var os: Boolean,
    var os_add_on: Boolean,
    var software_pc: Boolean,
    var game: Boolean,
    var isi_os: String?,
    var isi_game: String?,
    var isi_software_pc: String?,
    var catatan: String?,
    var antar: Boolean,
    var address: String?,
    var price: Int,
)