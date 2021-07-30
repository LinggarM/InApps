package com.incorps.inapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrdersCetak (
    var doc_id: String,
    var order_date : Long,
    var organisasi: String,
    var payment: String,
    var price: Long,
    var product: Long,
    var quantity: Long,
    var status: Long,
    var url_file_desain: String,
    var user: String
) : Parcelable