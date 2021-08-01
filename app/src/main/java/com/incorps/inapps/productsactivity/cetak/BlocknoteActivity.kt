package com.incorps.inapps.productsactivity.cetak

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.incorps.inapps.R
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.room.CartViewModel
import com.incorps.inapps.room.Cetak
import com.incorps.inapps.room.Desain
import com.incorps.inapps.utils.Tools

class BlocknoteActivity : AppCompatActivity() {
    private var urlFileDesain: String = ""
    private var qty: Int = 100
    private var organisasi: String = ""
    private val product: String = "301"
    private var price: Int = Tools.getProductPricebyId(product.toInt()).toInt() * qty
    private var user: String = ""

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private val fileDesainRef = Firebase.storage.reference
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

    private lateinit var btnFileDesain: ImageButton
    private lateinit var tvNamaFile: TextView
    private lateinit var progressUpload: ProgressBar
    private lateinit var tvUploading: TextView
    private lateinit var tvFileUploaded: TextView

    private lateinit var btnminQty: MaterialButton
    private lateinit var tvQty: TextView
    private lateinit var btnPlusQty: MaterialButton

    private lateinit var editOrganisasi: TextInputEditText

    private lateinit var tvPrice: TextView
    private lateinit var btnAddtoCart: MaterialButton

    companion object {
        private const val PICK_FILE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_cetak)

        imgBack = findViewById(R.id.img_back_button)
        imgFavorite = findViewById(R.id.img_favorite)
        imgProduct = findViewById(R.id.img_product)
        tvProductTitle = findViewById(R.id.tv_product_title)
        tvHarga = findViewById(R.id.tv_harga)
        tvDeskripsi = findViewById(R.id.tv_deskripsi)
        btnFileDesain = findViewById(R.id.btn_file_desain)
        tvNamaFile = findViewById(R.id.tv_nama_file)
        progressUpload = findViewById(R.id.progress_upload)
        tvUploading = findViewById(R.id.tv_uploading)
        tvFileUploaded = findViewById(R.id.tv_file_uploaded)
        btnminQty = findViewById(R.id.btn_min_qty)
        tvQty = findViewById(R.id.tv_qty)
        btnPlusQty = findViewById(R.id.btn_plus_qty)
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
        tvHarga.text = "Rp. $harga/ pcs"
        tvDeskripsi.text = resources.getString(R.string.deskripsi_blocknote_cetak)

        // File Pendukung
        btnFileDesain.setOnClickListener {
            val pickFile = Intent(Intent.ACTION_GET_CONTENT)
            pickFile.type = "*/*"
            startActivityForResult(Intent.createChooser(pickFile, "Select File"), PICK_FILE)
        }

        // Quantity
        tvQty.text = qty.toString()
        buttonQuantity()

        // Layout Bottom
        tvPrice.text = Tools.getCurrencySeparator(price.toLong())

        btnAddtoCart.setOnClickListener {
            organisasi = editOrganisasi.text.toString()
            if (urlFileDesain == "") {
                Tools.showCustomToastFailed(
                    this,
                    layoutInflater,
                    resources,
                    "Upload desain untuk dicetak!"
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
                        val cetak = Cetak(0, user, product.toInt(), urlFileDesain, qty, organisasi, price)
                        cartViewModel.insertCetak(cetak)
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
                urlFileDesain = "${user}_${currentTime}"
                fileDesainRef.child("file_desain_cetak/$urlFileDesain").putFile(dataUri)
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
                            resources.getString(R.string.upload_file_gagal)
                        )
                        urlFileDesain = ""
                    }
            }
        }
    }

    private fun buttonQuantity() {
        btnPlusQty.setOnClickListener {
            val currentQty = tvQty.text.toString().toInt()
            val newQty = (currentQty + 1)
            qty = newQty
            tvQty.text = newQty.toString()
            updatePrice()
        }
        btnminQty.setOnClickListener {
            val currentQty = tvQty.text.toString().toInt()
            if (currentQty != 100) {
                val newQty = (currentQty - 1)
                qty = newQty
                tvQty.text = qty.toString()
                updatePrice()
            }
        }
    }

    private fun updatePrice() {
        price = qty * Tools.getProductPricebyId(product.toInt()).toInt()
        tvPrice.text = Tools.getCurrencySeparator(price.toLong())
    }
}