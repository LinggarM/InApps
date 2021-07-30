package com.incorps.inapps

import android.app.AlertDialog
import android.content.Intent
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
import com.google.firebase.storage.ktx.storage
import com.incorps.inapps.model.OrdersDesain
import com.incorps.inapps.utils.Tools

class OrderDetailDesain : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var tvJenisDesain: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvWaktuPengerjaan: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvTitleFilePendukung: TextView
    private lateinit var btnFilePendukung: MaterialButton
    private lateinit var tvTitleOrganisasi: TextView
    private lateinit var tvOrganisasi: TextView
    private lateinit var tvPayment: TextView
    private lateinit var tvRekening: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvPrice: TextView
    private lateinit var btnCancelOrder: Button

    private lateinit var orderDesain: OrdersDesain

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private lateinit var db: FirebaseFirestore
    private val filePendukungRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail_desain)

        imgBack = findViewById(R.id.img_back_button)
        tvJenisDesain = findViewById(R.id.tv_jenis_desain)
        tvDesc = findViewById(R.id.tv_desc)
        tvWaktuPengerjaan = findViewById(R.id.tv_waktu_pengerjaan)
        tvEmail = findViewById(R.id.tv_email)
        tvTitleOrganisasi = findViewById(R.id.tv_title_organisasi)
        tvOrganisasi = findViewById(R.id.tv_organisasi)
        tvTitleFilePendukung = findViewById(R.id.tv_title_file_pendukung)
        btnFilePendukung = findViewById(R.id.btn_file_pendukung)
        tvPayment = findViewById(R.id.tv_payment)
        tvRekening = findViewById(R.id.tv_rekening)
        tvStatus = findViewById(R.id.tv_status)
        tvPrice = findViewById(R.id.tv_price)
        btnCancelOrder = findViewById(R.id.btn_cancel_order)

        db = Firebase.firestore

        // Get Object Data
        orderDesain = intent.getParcelableExtra("order_desain")!!

        // Tombol Back
        imgBack.setOnClickListener {
            finish()
        }

        // Tombol Download File Pendukung
        if (orderDesain.url_file_pendukung == "") {
            tvTitleFilePendukung.visibility = View.GONE
            btnFilePendukung.visibility = View.GONE
        } else {
            btnFilePendukung.setOnClickListener {
                val fileName = orderDesain.url_file_pendukung
                filePendukungRef.child("file_pendukung_desain/$fileName").downloadUrl
                    .addOnSuccessListener {
                        val uriFile = it
                        val intentFile = Intent(Intent.ACTION_VIEW, uriFile)
                        startActivity(intentFile)
                    }.addOnFailureListener {
                        Tools.showCustomToastFailed(
                            this, layoutInflater, resources,
                            "Download File Gagal"
                        )
                    }
            }
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
                    db.collection("orders_desain").document(orderDesain.doc_id)
                        .update(mapOf(
                            "status" to 3
                        )).addOnSuccessListener {
                            Tools.showCustomToastSuccess(context, layoutInflater, resources, "Hapus Pesanan Berhasil!")
                            finish()
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
        if (orderDesain.status != 0.toLong()) {
            btnCancelOrder.visibility = View.GONE
        }

        // Jenis Desain
        tvJenisDesain.text = Tools.getProductNameById(orderDesain.product.toInt())

        // Deskripsi Desain
        tvDesc.text = orderDesain.deskripsi_desain

        // Waktu Pengerjaan
        tvWaktuPengerjaan.text = orderDesain.waktu_pengerjaan.toString()

        // Email
        tvEmail.text = orderDesain.email_pengiriman

        // Organisasi
        tvOrganisasi.text = orderDesain.organisasi
        if (orderDesain.organisasi == "") {
            tvTitleOrganisasi.visibility = View.GONE
            tvOrganisasi.visibility = View.GONE
        }

        // Metode Pembayaran
        tvPayment.text = orderDesain.payment
        if (orderDesain.payment == "transfer") {
            tvRekening.text = "No Rekening untuk Pembayaran adalah ${resources.getString(R.string.rekening_incorps)} atas nama ${resources.getString(R.string.rekening_incorps_atas_nama)}"
            tvRekening.visibility = View.VISIBLE
        }

        // Status
        when (orderDesain.status) {
            0.toLong() -> {
                tvStatus.text = "Dipesan"
                tvStatus.background = resources.getDrawable(R.drawable.bg_green)
            }
            1.toLong() -> {
                tvStatus.text = "Diproses"
                tvStatus.background = resources.getDrawable(R.drawable.bg_green)
            }
            2.toLong() -> {
                tvStatus.text = "Selesai"
                tvStatus.background = resources.getDrawable(R.drawable.bg_green)
            }
            3.toLong() -> {
                tvStatus.text = "Dibatalkan"
                tvStatus.background = resources.getDrawable(R.drawable.bg_red)
            }
        }

        // Harga
        tvPrice.text = Tools.getCurrencySeparator(orderDesain.price)

    }
}