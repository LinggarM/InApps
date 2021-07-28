package com.incorps.inapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrdersInstall (
    var doc_id: String
) : Parcelable