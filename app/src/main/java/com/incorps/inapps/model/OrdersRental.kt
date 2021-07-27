package com.incorps.inapps.model

data class OrdersRental(
    var doc_id: String,
    var url_identitas: String,
    var product: String,
    var tgl_peminjaman: Long,
    var quantity: Long,
    var address: String,
    var organisasi: String,
    var lama_peminjaman: Long,
    var antar: Boolean,
    var price: Long,
    var tgl_pengembalian: Long,
    var payment: String,
    var user: String,
    var status: Long,
)