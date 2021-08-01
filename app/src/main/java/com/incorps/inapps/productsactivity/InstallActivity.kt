package com.incorps.inapps.productsactivity

import android.app.AlertDialog
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
import com.incorps.inapps.R
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.room.CartViewModel
import com.incorps.inapps.room.Cetak
import com.incorps.inapps.room.Install
import com.incorps.inapps.utils.Tools

class InstallActivity : AppCompatActivity() {
    private var os: Boolean = false
    private var osAddOn: Boolean = false
    private var software: Boolean = false
    private var game: Boolean = false
    private var isiOs: String = ""
    private var isiSoftware: String = ""
    private var isiGame: String = ""
    private var catatan: String = ""
    private var antar: Boolean = false
    private var address: String = ""
    private var price: Int = 0
    private val product: String = "401"
    private var user: String = ""

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private lateinit var cartViewModel : CartViewModel

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private lateinit var imgBack: ImageView
    private lateinit var imgFavorite: ImageView
    private var isFav = false

    private lateinit var btnOS: TextView
    private lateinit var btnSoftware: TextView
    private lateinit var btnGame: TextView
    private lateinit var tvTitleOS: TextView
    private lateinit var tvDeskripsi: TextView
    private lateinit var layoutOS: TextInputLayout
    private lateinit var editTextOS: TextInputEditText
    private lateinit var radioAddOn: Switch
    private lateinit var tvTitleSoftware: TextView
    private lateinit var layoutSoftware: TextInputLayout
    private lateinit var editTextSoftware: TextInputEditText
    private lateinit var tvTitleGame: TextView
    private lateinit var layoutGame: TextInputLayout
    private lateinit var editTextGame: TextInputEditText

    private lateinit var editCatatan: TextInputEditText

    private lateinit var spinnerPengambilanBarang: Spinner
    private lateinit var textLayoutAlamat: TextInputLayout
    private lateinit var tvAlamat: TextView
    private lateinit var editAlamat: TextInputEditText

    private lateinit var tvPrice: TextView
    private lateinit var btnAddtoCart: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_install)

        imgBack = findViewById(R.id.img_back_button)
        imgFavorite = findViewById(R.id.img_favorite)

        btnOS = findViewById(R.id.btn_os)
        btnSoftware = findViewById(R.id.btn_software)
        btnGame = findViewById(R.id.btn_game)
        tvTitleOS = findViewById(R.id.tv_title_os)
        tvDeskripsi = findViewById(R.id.tv_deskripsi)
        layoutOS = findViewById(R.id.layout_os)
        editTextOS = findViewById(R.id.edittext_os)
        radioAddOn = findViewById(R.id.radio_addon)
        tvTitleSoftware = findViewById(R.id.tv_title_software)
        layoutSoftware = findViewById(R.id.layout_software)
        editTextSoftware = findViewById(R.id.edittext_software)
        tvTitleGame = findViewById(R.id.tv_title_game)
        layoutGame = findViewById(R.id.layout_game)
        editTextGame = findViewById(R.id.edittext_game)

        editCatatan = findViewById(R.id.edittext_catatan)

        spinnerPengambilanBarang = findViewById(R.id.spinner_pengambilan_barang)
        textLayoutAlamat = findViewById(R.id.text_layout_alamat)
        editAlamat = findViewById(R.id.edittext_alamat)
        tvAlamat = findViewById(R.id.tv_alamat)
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

        // Tipe Instalasi
        btnOS.setOnClickListener {
            if (os) {
                os = false
                btnOS.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_button_install))
                tvTitleOS.visibility = View.GONE
                layoutOS.visibility = View.GONE
                radioAddOn.visibility = View.GONE
            } else {
                os = true
                btnOS.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_button_install_selected))
                tvTitleOS.visibility = View.VISIBLE
                layoutOS.visibility = View.VISIBLE
                radioAddOn.visibility = View.VISIBLE
            }
        }
        btnSoftware.setOnClickListener {
            if (software) {
                software = false
                btnSoftware.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_button_install))
                tvTitleSoftware.visibility = View.GONE
                layoutSoftware.visibility = View.GONE
            } else {
                software = true
                btnSoftware.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_button_install_selected))
                tvTitleSoftware.visibility = View.VISIBLE
                layoutSoftware.visibility = View.VISIBLE
            }
        }
        btnGame.setOnClickListener {
            if (game) {
                game = false
                btnGame.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_button_install))
                tvTitleGame.visibility = View.GONE
                layoutGame.visibility = View.GONE
            } else {
                game = true
                btnGame.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_button_install_selected))
                tvTitleGame.visibility = View.VISIBLE
                layoutGame.visibility = View.VISIBLE
            }
        }
        radioAddOn.setOnCheckedChangeListener { compoundButton, isChecked ->
            osAddOn = isChecked
        }

        // Catatan
        catatan = editCatatan.text.toString()

        // Pengambilan Barang
        spinnerPengambilan()
        val alamatIncorps = resources.getString(R.string.alamat_incorps)
        tvAlamat.text = "Alamat pengambilan laptop adalah $alamatIncorps"

        // Layout Bottom
        tvPrice.text = price.toString()
        btnAddtoCart.setOnClickListener {
            isiOs = editTextOS.text.toString()
            isiSoftware = editTextSoftware.text.toString()
            isiGame = editTextGame.text.toString()
            catatan = editCatatan.text.toString()
            address = editAlamat.text.toString()

            if (!os && !software && !game) {
                Tools.showCustomToastFailed(this, layoutInflater, resources, "Pilih minimal 1 tipe instalasi")
            } else if (os && isiOs == "") {
                editTextOS.setError("Kolom OS harus diisi")
            } else if (software && isiSoftware == "") {
                editTextSoftware.setError("Kolom Software harus diisi")
            } else if (game && isiGame == "") {
                editTextGame.setError("Kolom Game harus diisi")
            } else if (antar && address == "") {
                editAlamat.setError("Alamat pengantaran harus diisi!")
            } else {
                price = getPrice()
                tvPrice.text = price.toString()

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
                        val install = Install(0, user, product.toInt(), os, osAddOn, software, game, isiOs, isiGame, isiSoftware, catatan, antar, address, price)
                        cartViewModel.insertInstall(install)
                        Tools.showToastAddtoCart(this@InstallActivity, layoutInflater, product)
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

    private fun getPrice(): Int {
        var priceOS = 0
        var priceSoftware = 0
        var priceGame = 0

        if (os) {
            priceOS = 40000 * Tools.countCommaSeparator(isiOs)
        }
        if (software) {
            priceSoftware = 5000 * Tools.countCommaSeparator(isiSoftware)
        }
        if (game) {
            priceGame = 10000 * Tools.countCommaSeparator(isiGame)
        }
        return priceOS + priceSoftware + priceGame
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
}