package com.incorps.inapps.productsactivity.desain

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.incorps.inapps.R
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.room.CartViewModel
import com.incorps.inapps.room.Desain
import com.incorps.inapps.utils.Tools

class BlocknoteActivity : AppCompatActivity() {
    private var deskripsi_desain: String = ""
    private var waktuPengerjaan: Int = 5
    private var urlFilePendukung: String = ""
    private var emailPengiriman: String = ""
    private var organisasi: String = ""
    private val product: String = "205"
    private var price: Int = Tools.getProductPricebyId(product.toInt()).toInt()
    private var user: String = ""

    companion object {
        private const val PICK_FILE = 1
    }

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private val filePendukungRef = Firebase.storage.reference
    private lateinit var cartViewModel : CartViewModel

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private lateinit var imgBack: ImageView
    private lateinit var imgFavorite: ImageView
    private var isFav = false

    private lateinit var imgProduct: ImageView
    private lateinit var tvProductTitle: TextView
    private lateinit var tvHarga: TextView
    private lateinit var tvDeskripsi: TextView

    private lateinit var editDeskripsiDesain: TextInputEditText

    private lateinit var btnFilePendukung: ImageButton
    private lateinit var tvNamaFile: TextView
    private lateinit var progressUpload: ProgressBar
    private lateinit var tvUploading: TextView
    private lateinit var tvFileUploaded: TextView

    private lateinit var spinnerWaktuPengerjaan: Spinner

    private lateinit var editEmailPengiriman: TextInputEditText

    private lateinit var editOrganisasi: TextInputEditText

    private lateinit var tvPrice: TextView
    private lateinit var btnAddtoCart: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_desain)

        imgBack = findViewById(R.id.img_back_button)
        imgFavorite = findViewById(R.id.img_favorite)
        imgProduct = findViewById(R.id.img_product)
        tvProductTitle = findViewById(R.id.tv_product_title)
        tvHarga = findViewById(R.id.tv_harga)
        tvDeskripsi = findViewById(R.id.tv_deskripsi)
        editDeskripsiDesain = findViewById(R.id.edittext_deskripsi_desain)
        btnFilePendukung = findViewById(R.id.btn_file_pendukung)
        tvNamaFile = findViewById(R.id.tv_nama_file)
        progressUpload = findViewById(R.id.progress_upload)
        tvUploading = findViewById(R.id.tv_uploading)
        tvFileUploaded = findViewById(R.id.tv_file_uploaded)
        spinnerWaktuPengerjaan = findViewById(R.id.spinner_waktu_pengerjaan)
        editEmailPengiriman = findViewById(R.id.edittext_email_pengiriman)
        editOrganisasi = findViewById(R.id.edittext_organisasi)
        tvPrice = findViewById(R.id.tv_price)
        btnAddtoCart = findViewById(R.id.btn_add_to_cart)

        accountSessionPreferences = AccountSessionPreferences(this)
        user = accountSessionPreferences.idUser

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

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
        val harga = Tools.getCurrencySeparator(Tools.getProductPricebyId(product.toInt()))
        tvHarga.text = "Rp. $harga"
        tvDeskripsi.text = resources.getString(R.string.deskripsi_blocknote)

        // File Pendukung
        btnFilePendukung.setOnClickListener {
            val pickFile = Intent(Intent.ACTION_GET_CONTENT)
            pickFile.type = ("application/*")
            startActivityForResult(
                Intent.createChooser(pickFile, "Select File"),
                PICK_FILE
            )
        }

        // Spinner
        setSpinnerWaktuPengerjaan()

        // Layout Bottom
        tvPrice.text = price.toString()

        btnAddtoCart.setOnClickListener {
            deskripsi_desain = editDeskripsiDesain.text.toString()
            emailPengiriman = editEmailPengiriman.text.toString()
            organisasi = editOrganisasi.text.toString()
            if (deskripsi_desain == "") {
                Tools.showCustomToastFailed(
                    this,
                    layoutInflater,
                    resources,
                    "Deskripsi desain harus diisi!"
                )
            } else if (emailPengiriman == "") {
                Tools.showCustomToastFailed(
                    this,
                    layoutInflater,
                    resources,
                    "Email pengiriman harus diisi!"
                )
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
                        val desain = Desain(0, user, product.toInt(), deskripsi_desain, waktuPengerjaan, urlFilePendukung, emailPengiriman, organisasi, price)
                        cartViewModel.insertDesain(desain)
                        Tools.showToastAddtoCart(this@BlocknoteActivity, layoutInflater, product)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_FILE) {
            data?.data?.let { dataUri ->
                val fileName = Tools.getFileName(dataUri, contentResolver)
                tvNamaFile.text = fileName
                tvNamaFile.visibility = View.VISIBLE
                progressUpload.visibility = View.VISIBLE

                // Upload File
                val currentTime = System.currentTimeMillis().toString()
                urlFilePendukung = "${user}_${currentTime}"
                filePendukungRef.child("file_pendukung_desain/$urlFilePendukung").putFile(dataUri)
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
                            "Upload File Gagal"
                        )
                        urlFilePendukung = ""
                    }
            }
        }
    }

    private fun setSpinnerWaktuPengerjaan() {
        val waktu = resources.getStringArray(R.array.waktu_pengerjaan)
        spinnerWaktuPengerjaan.adapter = ArrayAdapter(this, R.layout.item_spinner, waktu)
        spinnerWaktuPengerjaan.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            waktuPengerjaan = 5
                            updatePrice()
                        }
                        1 -> {
                            waktuPengerjaan = 3
                            updatePrice()
                        }
                        2 -> {
                            waktuPengerjaan = 2
                            updatePrice()
                        }
                        3 -> {
                            waktuPengerjaan = 1
                            updatePrice()
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }

    private fun updatePrice() {
        when (waktuPengerjaan) {
            5 -> {
                price = Tools.getProductPricebyId(product.toInt()).toInt()
            }
            3 -> {
                price = Tools.getProductPricebyId(product.toInt()).toInt() + 15000
            }
            2 -> {
                price = Tools.getProductPricebyId(product.toInt()).toInt() + 30000
            }
            1 -> {
                price = Tools.getProductPricebyId(product.toInt()).toInt() + 40000
            }
        }
        tvPrice.text = Tools.getCurrencySeparator(price.toLong())
    }
}