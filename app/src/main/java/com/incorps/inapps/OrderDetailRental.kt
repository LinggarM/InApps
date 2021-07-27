package com.incorps.inapps

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.model.OrdersRental
import com.incorps.inapps.utils.Tools

class OrderDetailRental : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvLamaPeminjaman: TextView
    private lateinit var tvTglPeminjaman: TextView
    private lateinit var tvWaktuPeminjaman: TextView
    private lateinit var tvTglPengembalian: TextView
    private lateinit var tvWaktuPengembalian: TextView
    private lateinit var tvQuantity: TextView
    private lateinit var tvOrganisasi: TextView
    private lateinit var tvAntar: TextView
    private lateinit var tvTitleAlamat: TextView
    private lateinit var tvAlamat: TextView
    private lateinit var btnOpenMaps: MaterialButton
    private lateinit var tvPayment: TextView
    private lateinit var tvRekening: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvPrice: TextView
    private lateinit var btnCancelOrder: Button

    private lateinit var orderRental: OrdersRental

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail_rental)

        imgBack = findViewById(R.id.img_back_button)
        tvTitle = findViewById(R.id.tv_product_title)
        tvLamaPeminjaman = findViewById(R.id.tv_lama_peminjaman)
        tvTglPeminjaman = findViewById(R.id.tv_tgl_peminjaman)
        tvWaktuPeminjaman = findViewById(R.id.tv_waktu_peminjaman)
        tvTglPengembalian = findViewById(R.id.tv_tgl_pengembalian)
        tvWaktuPengembalian = findViewById(R.id.tv_waktu_pengembalian)
        tvQuantity = findViewById(R.id.tv_quantity)
        tvOrganisasi = findViewById(R.id.tv_organisasi)
        tvAntar = findViewById(R.id.tv_antar)
        tvTitleAlamat = findViewById(R.id.tv_title_alamat)
        tvAlamat = findViewById(R.id.tv_alamat)
        btnOpenMaps = findViewById(R.id.btn_open_maps)
        tvPayment = findViewById(R.id.tv_payment)
        tvRekening = findViewById(R.id.tv_rekening)
        tvStatus = findViewById(R.id.tv_status)
        tvPrice = findViewById(R.id.tv_price)
        btnCancelOrder = findViewById(R.id.btn_cancel_order)

        db = Firebase.firestore

        // Get Object Data
        orderRental = intent.getParcelableExtra("order_rental")!!

        // Tombol Back
        imgBack.setOnClickListener {
            finish()
        }

        // Tombol Open Maps
        btnOpenMaps.setOnClickListener {
            val uriMaps = Uri.parse(resources.getString(R.string.url_alamat_incorps))
            val intentMaps = Intent(Intent.ACTION_VIEW, uriMaps)
            startActivity(intentMaps)
        }

        // Tombol Cancel order
        btnCancelOrder.setOnClickListener {

            //set alert dialog builder
            builder = AlertDialog.Builder(this)

            //set title for alert dialog
            builder.setTitle(R.string.hapus_pesanan)

            //set message for alert dialog
            builder.setMessage(R.string.message_hapus_pesanan)
            builder.setIcon(R.drawable.ic_baseline_warning_24)

            //set positive and negative button
            builder.apply {
                setPositiveButton("Yes") { dialogInterface, i ->
                    db.collection("orders_rental").document(orderRental.doc_id)
                        .update(mapOf(
                            "status" to 3
                        )).addOnSuccessListener {
                            Tools.showCustomToastSuccess(context, layoutInflater, resources, "Hapus Pesanan Berhasil!")
                            finish()
//                            setResult(Activity.RESULT_OK)
                        }.addOnFailureListener {
                            Tools.showCustomToastFailed(context, layoutInflater, resources, "Hapus Pesanan Gagal!")
                        }
                }
                setNegativeButton("No") { dialogInterface, i ->

                }
            }

            // Create the AlertDialog
            alertDialog = builder.create()
            alertDialog.show()
        }

        // Tombol Cancel hanya jika masih Dipesan
        if (orderRental.status != 0.toLong()) {
            btnCancelOrder.visibility = View.GONE
        }

        // Nama Produk
        tvTitle.text = Tools.getProductNameById(orderRental.product.toInt())

        // Lama Peminjaman
        val textLamaPeminjaman: String = when {
            orderRental.lama_peminjaman > 24.toLong() -> {
                val hari = orderRental.lama_peminjaman / 24
                "$hari Hari"
            }
            orderRental.lama_peminjaman == 24.toLong() -> {
                "1 Hari (24 Jam)"
            }
            else -> {
                "${orderRental.lama_peminjaman} Jam"
            }
        }
        tvLamaPeminjaman.text = textLamaPeminjaman

        // Tanggal Peminjaman
        tvTglPeminjaman.text = Tools.getDate(orderRental.tgl_peminjaman, "EEE, dd MM yyyy")
        tvWaktuPeminjaman.text = Tools.getDate(orderRental.tgl_peminjaman, "HH:mm")

        // Tanggal Pengembalian
        tvTglPengembalian.text = Tools.getDate(orderRental.tgl_pengembalian, "EEE, dd MM yyyy")
        tvWaktuPengembalian.text = Tools.getDate(orderRental.tgl_pengembalian, "HH:mm")

        // Quantity
        val textquantity = "${orderRental.quantity} Buah"
        tvQuantity.text = textquantity

        // Organisasi
        tvOrganisasi.text = orderRental.organisasi

        // Pengambilan Barang
        if (orderRental.antar) {
            tvAntar.text = "Di Antar"
            tvTitleAlamat.text = "Alamat Pengantaran"
            tvAlamat.text = orderRental.address
        } else {
            tvAntar.text = "Ambil Sendiri"
            tvTitleAlamat.text = "Alamat Pengambilan Barang"
            tvAlamat.text = resources.getString(R.string.alamat_incorps)
            btnOpenMaps.visibility = View.VISIBLE
        }

        // Metode Pembayaran
        tvPayment.text = orderRental.payment
        if (orderRental.payment == "transfer") {
            tvRekening.text = "No Rekening untuk Pembayaran adalah ${resources.getString(R.string.rekening_incorps)} atas nama ${resources.getString(R.string.rekening_incorps_atas_nama)}"
            tvRekening.visibility = View.VISIBLE
        }

        // Status
        when (orderRental.status) {
            0.toLong() -> {
                tvStatus.text = "Dipesan"
                tvStatus.background = resources.getDrawable(R.drawable.bg_tersedia)
            }
            1.toLong() -> {
                tvStatus.text = "Diproses"
                tvStatus.background = resources.getDrawable(R.drawable.bg_tersedia)
            }
            2.toLong() -> {
                tvStatus.text = "Selesai"
                tvStatus.background = resources.getDrawable(R.drawable.bg_tersedia)
            }
            3.toLong() -> {
                tvStatus.text = "Dibatalkan"
                tvStatus.background = resources.getDrawable(R.drawable.bg_red)
            }
        }

        // Harga
        tvPrice.text = Tools.getCurrencySeparator(orderRental.price)
    }
}