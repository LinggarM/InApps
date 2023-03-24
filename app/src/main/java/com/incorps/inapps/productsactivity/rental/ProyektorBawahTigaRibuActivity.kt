package com.incorps.inapps.productsactivity.rental

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.incorps.inapps.R
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.room.CartViewModel
import com.incorps.inapps.room.Rental
import com.incorps.inapps.utils.Tools
import java.util.*

class ProyektorBawahTigaRibuActivity : AppCompatActivity() {

    private var paketRental: Int = 0
    private var jam: Int = 0
    private var qty: Int = 1
    private var price: Int = 0
    private var tglPeminjaman: Long = 0
    private var tglPengembalian: Long = 0
    private var fileNameUpload: String = ""
    private var organisasi: String = ""
    private var antar: Boolean = false
    private var address: String = ""
    private val product: String = "102"
    private var user: String = ""
    private var tersedia: Long = 0

    companion object {
        private const val PICK_IMAGE = 1
    }

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private val fotoIdentitasRef = Firebase.storage.reference
    private lateinit var db: FirebaseFirestore
    private lateinit var cartViewModel : CartViewModel

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private lateinit var imgBack: ImageView
    private lateinit var imgFavorite: ImageView
    private var isFav = false

    private lateinit var imgProduct: ImageView
    private lateinit var tvProductTitle: TextView
    private lateinit var tvTersedia: TextView

    private lateinit var btnDeskripsi: MaterialButton
    private lateinit var btnSpesifikasi: MaterialButton
    private lateinit var btnHarga: MaterialButton

    private lateinit var tvDeskripsi: TextView
    private lateinit var tvSpesifikasi: TextView
    private lateinit var tvHarga: TextView

    private lateinit var tvTitleLamaPeminjaman: TextView
    private lateinit var layoutLamaPeminjaman: RelativeLayout
    private lateinit var spinnerLamaPeminjaman: Spinner
    private lateinit var textLayoutHari: TextInputLayout
    private lateinit var editHariPeminjaman: TextInputEditText

    private lateinit var btnTimeDatePeminjaman: Button
    private lateinit var layoutTglAtas: LinearLayout
    private lateinit var layoutTglSampai: LinearLayout
    private lateinit var layoutTglBawah: LinearLayout
    private lateinit var tvTglPeminjaman: TextView
    private lateinit var tvJamPeminjaman: TextView
    private lateinit var tvTglPengembalian: TextView
    private lateinit var tvJamPengembalian: TextView

    private lateinit var btnminQty: MaterialButton
    private lateinit var tvQty: TextView
    private lateinit var btnPlusQty: MaterialButton

    private lateinit var btnFotoIdentitas: ImageButton
    private lateinit var tvNamaFile: TextView
    private lateinit var progressUpload: ProgressBar
    private lateinit var tvUploading: TextView
    private lateinit var tvFileUploaded: TextView

    private lateinit var editOrganisasi: TextInputEditText

    private lateinit var spinnerPengambilanBarang: Spinner
    private lateinit var textLayoutAlamat: TextInputLayout
    private lateinit var tvAlamat: TextView
    private lateinit var editAlamat: TextInputEditText

    private lateinit var tvPrice: TextView
    private lateinit var btnAddtoCart: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_rental)

        imgBack = findViewById(R.id.img_back_button)
        imgFavorite = findViewById(R.id.img_favorite)

        imgProduct = findViewById(R.id.img_product)
        tvProductTitle = findViewById(R.id.tv_product_title)
        tvTersedia = findViewById(R.id.tv_tersedia)

        btnDeskripsi = findViewById(R.id.btn_deskripsi)
        btnSpesifikasi = findViewById(R.id.btn_spesifikasi)
        btnHarga = findViewById(R.id.btn_harga)

        tvDeskripsi = findViewById(R.id.tv_deskripsi)
        tvSpesifikasi = findViewById(R.id.tv_spesifikasi)
        tvHarga = findViewById(R.id.tv_harga)

        tvTitleLamaPeminjaman = findViewById(R.id.tv_title_lama_peminjaman)
        layoutLamaPeminjaman = findViewById(R.id.layout_lama_peminjaman)
        spinnerLamaPeminjaman = findViewById(R.id.spinner_lama_peminjaman)
        textLayoutHari = findViewById(R.id.text_layout_hari)
        editHariPeminjaman = findViewById(R.id.edittext_hari_peminjaman)

        btnTimeDatePeminjaman = findViewById(R.id.btn_time_date_peminjaman)
        layoutTglAtas = findViewById(R.id.layout_tgl_atas)
        layoutTglSampai = findViewById(R.id.layout_tgl_sampai)
        layoutTglBawah = findViewById(R.id.layout_tgl_bawah)
        tvTglPeminjaman = findViewById(R.id.tv_tgl_peminjaman)
        tvJamPeminjaman = findViewById(R.id.tv_jam_peminjaman)
        tvTglPengembalian = findViewById(R.id.tv_tgl_pengembalian)
        tvJamPengembalian = findViewById(R.id.tv_jam_pengembalian)

        btnminQty = findViewById(R.id.btn_min_qty)
        tvQty = findViewById(R.id.tv_qty)
        btnPlusQty = findViewById(R.id.btn_plus_qty)

        btnFotoIdentitas = findViewById(R.id.btn_foto_identitas)
        tvNamaFile = findViewById(R.id.tv_nama_file)
        progressUpload = findViewById(R.id.progress_upload)
        tvUploading = findViewById(R.id.tv_uploading)
        tvFileUploaded = findViewById(R.id.tv_file_uploaded)

        editOrganisasi = findViewById(R.id.edittext_organisasi)

        spinnerPengambilanBarang = findViewById(R.id.spinner_pengambilan_barang)
        textLayoutAlamat = findViewById(R.id.text_layout_alamat)
        tvAlamat = findViewById(R.id.tv_alamat)
        editAlamat = findViewById(R.id.edittext_alamat)

        tvPrice = findViewById(R.id.tv_price)
        btnAddtoCart = findViewById(R.id.btn_add_to_cart)

        accountSessionPreferences = AccountSessionPreferences(this)
        user = accountSessionPreferences.idUser

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        db = Firebase.firestore
        db.collection("products").document(product).get().addOnSuccessListener {
            it.let {
                tersedia = it.get("stock") as Long
                tvTersedia.text = "Tersedia : $tersedia"
                if (tersedia == 0.toLong()) {
                    tvQty.text = "0"
                    qty = 0
                } else {
                    tvQty.text = "1"
                    qty = 1
                }
            }
        }

        // Top Layout
        imgBack.setOnClickListener {
            finish()
        }
        imgFavorite.setOnClickListener {
            if (isFav) {
                Glide.with(this)
                    .load(resources.getDrawable(R.drawable.ic_baseline_favorite_border_24))
                    .into(imgFavorite)
                isFav = false
            } else {
                Glide.with(this)
                    .load(resources.getDrawable(R.drawable.ic_baseline_favorite_24))
                    .into(imgFavorite)
                isFav = true
            }
        }

        // Product
        Glide.with(this).load(Tools.getProductDrawableById(product.toInt())).into(imgProduct)
        tvProductTitle.text = Tools.getProductNameById(product.toInt())
        tvTersedia.text = "Tersedia : $tersedia"

        // Deskripsi Spesifikasi Harga
        tabsDeskripsi()

        // Lama Peminjaman
        setSpinnerLamaPeminjaman()

        // Tanggal Peminjaman
        val today = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            today.set(Calendar.HOUR_OF_DAY, hour)
            today.set(Calendar.MINUTE, minute)

            tglPeminjaman = today.timeInMillis
            val totalJam: Long = jam.toLong() * 3600000
            tglPengembalian = tglPeminjaman + totalJam

            tvTglPeminjaman.text = Tools.getDate(tglPeminjaman, "dd MMMM yyyy ")
            tvJamPeminjaman.text = Tools.getDate(tglPeminjaman, "HH:mm")
            tvTglPengembalian.text = Tools.getDate(tglPengembalian, "dd MMMM yyyy ")
            tvJamPengembalian.text = Tools.getDate(tglPengembalian, "HH:mm")

            layoutTglAtas.visibility = View.VISIBLE
            layoutTglSampai.visibility = View.VISIBLE
            layoutTglBawah.visibility = View.VISIBLE

            updatePrice()
        }

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                today.set(Calendar.YEAR, year)
                today.set(Calendar.MONTH, monthOfYear)
                today.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                TimePickerDialog(
                    this,
                    timeSetListener,
                    today.get(Calendar.HOUR_OF_DAY),
                    today.get(Calendar.MINUTE),
                    true
                ).show()
            }

        btnTimeDatePeminjaman.setOnClickListener {
            var hariPeminjamanValidate = false

            if ((paketRental == 0 && (editHariPeminjaman.text.toString() == ""))) {
                Tools.showCustomToastFailed(this, layoutInflater, resources, "Tentukan lama peminjaman terlebih dahulu")
            } else if (paketRental == 0 && (editHariPeminjaman.text.toString().toInt() <= 0)) {
                Tools.showCustomToastFailed(this, layoutInflater, resources, "Lama peminjaman minimal 1 hari")
            } else if (paketRental == 0) {
                jam = editHariPeminjaman.text.toString().toInt() * 24
                hariPeminjamanValidate = true
            } else if (paketRental !=0) {
                hariPeminjamanValidate = true
            }

            if (hariPeminjamanValidate) {
                // Show Dialog
                DatePickerDialog(
                    this,
                    dateSetListener,
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH)
                ).also {
                    it.datePicker.minDate = System.currentTimeMillis()
                    it.show()
                }
            }
        }


        // Quantity
        if (tersedia == 0.toLong()) {
            tvQty.text = "0"
            qty = 0
        }
        buttonQuantity()

        // Foto Identitas
        btnFotoIdentitas.setOnClickListener {
            val pickFile = Intent(Intent.ACTION_GET_CONTENT)
            pickFile.type = "image/*"
            startActivityForResult(Intent.createChooser(pickFile, "Select Picture"),
                PICK_IMAGE
            )
        }

        // Pengambilan Barang
        spinnerPengambilan()
        val alamatIncorps = resources.getString(R.string.alamat_incorps)
        tvAlamat.text = "Alamat pengambilan barang adalah $alamatIncorps"

        // Layout Bottom
        tvPrice.text = price.toString()

        btnAddtoCart.setOnClickListener {
            address = editAlamat.text.toString()
            organisasi = editOrganisasi.text.toString()
            var priceValidationSuccess = false
            if (paketRental == 0) {
                if (editHariPeminjaman.text.toString() == "") {
                    editHariPeminjaman.error = "Hari peminjaman harus diisi"
                } else {
                    jam = editHariPeminjaman.text.toString().toInt() * 24
                    if (jam <= 0) {
                        editHariPeminjaman.error = "Hari peminjaman tidak boleh 0"
                    } else {
                        priceValidationSuccess = true
                        updatePrice()
                    }
                }
            } else {
                updatePrice()
                priceValidationSuccess = true
            }
            if (priceValidationSuccess) {
                if (qty == 0) {
                    Tools.showCustomToastFailed(
                        this,
                        layoutInflater,
                        resources,
                        "Quantity tidak boleh 0"
                    )
                } else if (fileNameUpload == "") {
                    Tools.showCustomToastFailed(
                        this,
                        layoutInflater,
                        resources,
                        "Upload foto tanda identitas"
                    )
                } else if (tglPeminjaman == 0.toLong()) {
                    Tools.showCustomToastFailed(
                        this,
                        layoutInflater,
                        resources,
                        "Tanggal peminjaman harus diisi"
                    )
                } else if (antar && (address == "")) {
                    editAlamat.error = "Alamat pengantaran harus diisi!"
                } else {
                    //set alert dialog builder
                    builder = AlertDialog.Builder(this)

                    //set title for alert dialog
                    builder.setTitle("Add to Cart")

                    //set message for alert dialog
                    builder.setMessage("Tambahkan pesanan ke keranjang belanja?")
                    builder.setIcon(R.drawable.ic_baseline_shopping_cart_24_grey)

                    //set positive and negative button
                    builder.apply {
                        setPositiveButton("Yes") { dialogInterface, i ->
                            val rental = Rental(0, user, product.toInt(), fileNameUpload, tglPeminjaman, tglPengembalian, jam, qty, organisasi, antar, address, price)
                            cartViewModel.insertRental(rental)
                            Tools.showToastAddtoCart(this@ProyektorBawahTigaRibuActivity, layoutInflater, product)
                            finish()
                        }
                        setNegativeButton("No") { dialogInterface, i ->

                        }
                    }

                    // Create the AlertDialog
                    alertDialog = builder.create()
                    alertDialog.show()
                }
            }
        }
    }

    private fun tabsDeskripsi() {
        tvDeskripsi.text = resources.getString(R.string.deskripsi_proyektor_bawah_tiga_ribu)
        tvSpesifikasi.text = resources.getString(R.string.spesifikasi_proyektor_bawah_tiga_ribu)
        tvHarga.text = resources.getString(R.string.harga_proyektor_bawah_tiga_ribu)

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
        val paket = resources.getStringArray(R.array.paket_rental_proyektorbawahtigaribu)
        spinnerLamaPeminjaman.adapter = ArrayAdapter(this, R.layout.item_spinner, paket)
        spinnerLamaPeminjaman.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        textLayoutHari.visibility = View.VISIBLE
                        paketRental = 0
                        jam = 0
                        updatePrice()
                    }
                    1 -> {
                        textLayoutHari.visibility = View.GONE
                        paketRental = 1
                        jam = 12
                        tglPengembalian = tglPeminjaman + (jam * 3600000).toLong()
                        updatePrice()
                    }
                    2 -> {
                        textLayoutHari.visibility = View.GONE
                        paketRental = 2
                        jam = 6
                        tglPengembalian = tglPeminjaman + (jam * 3600000).toLong()
                        updatePrice()
                    }
                    3 -> {
                        textLayoutHari.visibility = View.GONE
                        paketRental = 3
                        jam = 1008
                        tglPengembalian = tglPeminjaman + (jam * 3600000).toLong()
                        updatePrice()
                    }
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
            if (currentQty.toLong() != tersedia) {
                val newQty = (currentQty + 1)
                qty = newQty
                tvQty.text = newQty.toString()
                if (paketRental == 0 && editHariPeminjaman.text.toString() != "") {
                    jam = editHariPeminjaman.text.toString().toInt() * 24
                }
                updatePrice()
            }
        }
        btnminQty.setOnClickListener {
            val currentQty = tvQty.text.toString().toInt()
            if (currentQty != 0) {
                val newQty = (currentQty - 1)
                qty = newQty
                tvQty.text = qty.toString()
                if (paketRental == 0 && editHariPeminjaman.text.toString() != "") {
                    jam = editHariPeminjaman.text.toString().toInt() * 24
                }
                updatePrice()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            data?.data?.let { dataUri ->
                val fileName = Tools.getFileName(dataUri, contentResolver)
                tvNamaFile.text = fileName
                tvNamaFile.visibility = View.VISIBLE
                progressUpload.visibility = View.VISIBLE

                // Upload File
                val idUser = user
                val currentTime = System.currentTimeMillis().toString()
                fileNameUpload = "${idUser}_${currentTime}"
                fotoIdentitasRef.child("foto_identitas/$fileNameUpload").putFile(dataUri)
                    .addOnProgressListener {
                        tvUploading.visibility = View.VISIBLE
                        val progress = (it.bytesTransferred / it.totalByteCount) * 100
                        progressUpload.progress = progress.toInt()
                    }.addOnSuccessListener { it ->
                        tvFileUploaded.visibility = View.VISIBLE
                        tvUploading.visibility = View.INVISIBLE
                    }.addOnFailureListener {
                        Tools.showCustomToastFailed(
                            this, layoutInflater, resources,
                            "Upload Foto Gagal"
                        )
                        fileNameUpload = ""
                    }
            }
        }
    }

    private fun spinnerPengambilan() {
        val pengambilan = resources.getStringArray(R.array.pengambilan_barang)
        spinnerPengambilanBarang.adapter = ArrayAdapter(this, R.layout.item_spinner, pengambilan)
        spinnerPengambilanBarang.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        antar = false
                        textLayoutAlamat.visibility = View.GONE
                        tvAlamat.visibility = View.VISIBLE
                    } else {
                        antar = true
                        textLayoutAlamat.visibility = View.VISIBLE
                        tvAlamat.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }

    private fun updatePrice() {
        when (paketRental) {
            0 -> {
                price = 75000 * (jam / 24) * qty
            }
            1 -> {
                price = 50000 * qty
            }
            2 -> {
                price = 30000 * qty
            }
            3 -> {
                price = 800000 * qty
            }
        }
        tvPrice.text = Tools.getCurrencySeparator(price.toLong())
    }
}