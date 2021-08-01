package com.incorps.inapps

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
import com.incorps.inapps.model.OrdersInstall
import com.incorps.inapps.model.OrdersRental
import com.incorps.inapps.utils.Tools

class OrderDetailInstall : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var tvJenisInstalasi: TextView
    private lateinit var tvOS: TextView
    private lateinit var tvSoftware: TextView
    private lateinit var tvGame: TextView
    private lateinit var tvIsiOs: TextView
    private lateinit var tvIsiSoftware: TextView
    private lateinit var tvIsiGame: TextView
    private lateinit var tvTitleCatatan: TextView
    private lateinit var tvCatatan: TextView
    private lateinit var tvAntar: TextView
    private lateinit var tvTitleAlamat: TextView
    private lateinit var tvAlamat: TextView
    private lateinit var btnOpenMaps: MaterialButton
    private lateinit var tvPayment: TextView
    private lateinit var tvRekening: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvPrice: TextView
    private lateinit var btnCancelOrder: Button

    private lateinit var orderInstall: OrdersInstall

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail_install)

        imgBack = findViewById(R.id.img_back_button)
        tvJenisInstalasi = findViewById(R.id.tv_jenis_instalasi)
        tvOS = findViewById(R.id.tv_os)
        tvSoftware = findViewById(R.id.tv_software)
        tvGame = findViewById(R.id.tv_game)
        tvIsiOs = findViewById(R.id.tv_isi_os)
        tvIsiSoftware = findViewById(R.id.tv_isi_software)
        tvIsiGame = findViewById(R.id.tv_isi_game)
        tvTitleCatatan = findViewById(R.id.tv_title_catatan)
        tvCatatan = findViewById(R.id.tv_catatan)
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
        orderInstall = intent.getParcelableExtra("order_install")!!

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
                    db.collection("orders_install").document(orderInstall.doc_id)
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
        if (orderInstall.status != 0.toLong()) {
            btnCancelOrder.visibility = View.GONE
        }

        // Jenis Instalasi
        var textOS = ""
        var textSoftware = ""
        var textGame = ""
        if (orderInstall.os) {
            textOS = "Operating System"
        }
        if (orderInstall.software_pc) {
            if (orderInstall.os) {
                textSoftware = ", Software"
            } else {
                textSoftware = "Software"
            }
        }
        if (orderInstall.game) {
            if (orderInstall.os || orderInstall.software_pc) {
                textGame = ", Game"
            } else {
                textGame = "Game"
            }
        }
        val textTitle = "$textOS$textSoftware$textGame"
        tvJenisInstalasi.text = textTitle

        // Daftar Instalasi
        if (orderInstall.os) {
            tvOS.visibility = View.VISIBLE
            tvIsiOs.visibility = View.VISIBLE
            if (orderInstall.os_add_on) {
                tvIsiOs.text = "${orderInstall.isi_os} (+ Aplikasi)"
            } else {
                tvIsiOs.text = orderInstall.isi_os
            }
        }
        if (orderInstall.software_pc) {
            tvSoftware.visibility = View.VISIBLE
            tvIsiSoftware.visibility = View.VISIBLE
            tvIsiSoftware.text = orderInstall.isi_software_pc
        }
        if (orderInstall.game) {
            tvGame.visibility = View.VISIBLE
            tvIsiGame.visibility = View.VISIBLE
            tvIsiGame.text = orderInstall.isi_game
        }

        // Catatan
        if (orderInstall.catatan != "") {
            tvCatatan.text = orderInstall.catatan
            tvTitleCatatan.visibility = View.VISIBLE
            tvCatatan.visibility = View.VISIBLE
        }

        // Pengambilan Barang
        if (orderInstall.antar) {
            tvAntar.text = "Di Antar"
            tvTitleAlamat.text = "Alamat Pengantaran"
            tvAlamat.text = orderInstall.address
        } else {
            tvAntar.text = "Ambil Sendiri"
            tvTitleAlamat.text = "Alamat Pengambilan Laptop"
            tvAlamat.text = resources.getString(R.string.alamat_incorps)
            btnOpenMaps.visibility = View.VISIBLE
        }

        // Metode Pembayaran
        tvPayment.text = orderInstall.payment
        if (orderInstall.payment == "transfer") {
            tvRekening.text = "No Rekening untuk Pembayaran adalah ${resources.getString(R.string.rekening_incorps)} atas nama ${resources.getString(R.string.rekening_incorps_atas_nama)}"
            tvRekening.visibility = View.VISIBLE
        }

        // Status
        when (orderInstall.status) {
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
        tvPrice.text = Tools.getCurrencySeparator(orderInstall.price)


    }
}