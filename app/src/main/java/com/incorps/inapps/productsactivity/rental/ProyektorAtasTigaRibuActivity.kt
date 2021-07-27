package com.incorps.inapps.productsactivity.rental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.incorps.inapps.R
import com.incorps.inapps.utils.Tools
import java.util.*

class ProyektorAtasTigaRibuActivity : AppCompatActivity() {

    private var time: Long = 0

    private lateinit var imgBack: ImageView

    private lateinit var btnDeskripsi: MaterialButton
    private lateinit var imgDeskripsi: ImageView
    private lateinit var btnSpesifikasi: MaterialButton
    private lateinit var imgSpesifikasi: ImageView
    private lateinit var btnHarga: MaterialButton
    private lateinit var imgHarga: ImageView

    private lateinit var tvDeskripsi: TextView
    private lateinit var tvSpesifikasi: TextView
    private lateinit var tvHarga: TextView
    private lateinit var spinnerLamaPeminjaman: Spinner
    private lateinit var textLayoutHari: TextInputLayout
    private lateinit var tvQty: TextView
    private lateinit var btnPlusQty: MaterialButton
    private lateinit var btnminQty: MaterialButton
    private lateinit var spinnerPengambilanBarang: Spinner
    private lateinit var textLayoutAlamat: TextInputLayout
    private lateinit var tvAlamat: TextView
    private lateinit var btnAddtoCart: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proyektor_atas_tiga_ribu)

        imgBack = findViewById(R.id.img_back_button)
        btnDeskripsi = findViewById(R.id.btn_deskripsi)
        btnSpesifikasi = findViewById(R.id.btn_spesifikasi)
        btnHarga = findViewById(R.id.btn_harga)
//        imgDeskripsi = findViewById(R.id.img_deskripsi)
//        imgSpesifikasi = findViewById(R.id.img_spesifikasi)
//        imgHarga = findViewById(R.id.img_harga)
        tvDeskripsi = findViewById(R.id.tv_deskripsi)
        tvSpesifikasi = findViewById(R.id.tv_spesifikasi)
        tvHarga = findViewById(R.id.tv_harga)
        spinnerLamaPeminjaman = findViewById(R.id.spinner_lama_peminjaman)
        textLayoutHari = findViewById(R.id.text_layout_hari)
        tvQty = findViewById(R.id.tv_qty)
        btnPlusQty = findViewById(R.id.btn_plus_qty)
        btnminQty = findViewById(R.id.btn_min_qty)
        spinnerPengambilanBarang = findViewById(R.id.spinner_pengambilan_barang)
        textLayoutAlamat = findViewById(R.id.edittext_alamat)
        tvAlamat = findViewById(R.id.tv_alamat)
        btnAddtoCart = findViewById(R.id.btn_add_to_cart)

        imgBack.setOnClickListener {
            finish()
        }

        buttonTabs()
        setSpinnerLamaPeminjaman()
        if (getTersedia() == 0) {
            tvQty.text = "0"
        }
        buttonQuantity()
        spinnerPengambilan()
        btnAddtoCart.setOnClickListener {
            Tools.showCustomToastSuccess(this, layoutInflater, resources, time.toString())
        }

        val dialogView = View.inflate(this, R.layout.item_cart_rental, null)
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()

//        dialogView.findViewById<View>(R.id.btn_time_date_peminjaman).setOnClickListener {
//            val datePicker = dialogView.findViewById<View>(R.id.date_picker_peminjaman) as DatePicker
//            val timePicker = dialogView.findViewById<View>(R.id.time_picker_peminjaman) as TimePicker
//            val calendar: Calendar = GregorianCalendar(
//                datePicker.year,
//                datePicker.month,
//                datePicker.dayOfMonth,
//                timePicker.currentHour,
//                timePicker.currentMinute
//            )
//            time = calendar.getTimeInMillis()
//            alertDialog.dismiss()
//        }
//        alertDialog.setView(dialogView)
//        alertDialog.show()
    }

    private fun spinnerPengambilan() {
        val pengambilan = resources.getStringArray(R.array.pengambilan_barang)
        spinnerPengambilanBarang.adapter = ArrayAdapter(this, R.layout.item_spinner, pengambilan)
        spinnerPengambilanBarang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    textLayoutAlamat.visibility = View.GONE
                    tvAlamat.visibility = View.VISIBLE
                } else {
                    textLayoutAlamat.visibility = View.VISIBLE
                    tvAlamat.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun buttonTabs() {
        btnDeskripsi.setOnClickListener {
            tvDeskripsi.visibility = View.VISIBLE
            tvSpesifikasi.visibility = View.GONE
            tvHarga.visibility = View.GONE

            btnDeskripsi.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            btnSpesifikasi.setTextColor(resources.getColor(R.color.white_bottom_nav))
            btnHarga.setTextColor(resources.getColor(R.color.white_bottom_nav))
        }
        btnSpesifikasi.setOnClickListener {
            tvDeskripsi.visibility = View.GONE
            tvSpesifikasi.visibility = View.VISIBLE
            tvHarga.visibility = View.GONE

            btnDeskripsi.setTextColor(resources.getColor(R.color.white_bottom_nav))
            btnSpesifikasi.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            btnHarga.setTextColor(resources.getColor(R.color.white_bottom_nav))
        }
        btnHarga.setOnClickListener {
            tvDeskripsi.visibility = View.GONE
            tvSpesifikasi.visibility = View.GONE
            tvHarga.visibility = View.VISIBLE

            btnDeskripsi.setTextColor(resources.getColor(R.color.white_bottom_nav))
            btnSpesifikasi.setTextColor(resources.getColor(R.color.white_bottom_nav))
            btnHarga.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        }
    }

    private fun setSpinnerLamaPeminjaman() {
        val paket = resources.getStringArray(R.array.paket_rental_proyektoratastigaribu)
        spinnerLamaPeminjaman.adapter = ArrayAdapter(this, R.layout.item_spinner, paket)
        spinnerLamaPeminjaman.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    textLayoutHari.visibility = View.VISIBLE
                } else {
                    textLayoutHari.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun buttonQuantity() {
        btnPlusQty.setOnClickListener {
            val currentQty = tvQty.text.toString().toInt()
            if (currentQty != getTersedia()) {
                tvQty.text = (currentQty + 1).toString()
            }
        }
        btnminQty.setOnClickListener {
            val currentQty = tvQty.text.toString().toInt()
            if (currentQty != 0) {
                tvQty.text = (currentQty - 1).toString()
            }
        }
    }

    private fun getTersedia(): Int {
        return 3
    }
}