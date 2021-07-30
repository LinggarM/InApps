package com.incorps.inapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrdersDesain (
    var doc_id: String,
    var deskripsi_desain: String,
    var email_pengiriman: String,
    var order_date : Long,
    var organisasi: String,
    var payment: String,
    var price: Long,
    var product: Long,
    var status: Long,
    var url_file_pendukung: String,
    var user: String,
    var waktu_pengerjaan: Long
) : Parcelable