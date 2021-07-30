package com.incorps.inapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrdersInstall (
    var doc_id: String,
    var address: String,
    var antar: Boolean,
    var catatan: String,
    var game: Boolean,
    var isi_game: String,
    var isi_os: String,
    var isi_software_pc: String,
    var order_date : Long,
    var os: Boolean,
    var os_add_on: Boolean,
    var payment: String,
    var price: Long,
    var product: Long,
    var software_pc: Boolean,
    var status: Long,
    var user: String,
) : Parcelable