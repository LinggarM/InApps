package com.incorps.inapps

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.room.*
import com.incorps.inapps.utils.Tools

class CheckoutActivity : AppCompatActivity() {
    private var user: String = ""
    private var payment: String = ""
    private var noRekening: Long = 0
    private var atasNama: String = ""

    private lateinit var spinnerPayment: Spinner
    private lateinit var tvTitleRekening: TextView
    private lateinit var textLayoutRekening: TextInputLayout
    private lateinit var edittextRekening: TextInputEditText
    private lateinit var tvTitleAtasnama: TextView
    private lateinit var textLayoutAtasnama: TextInputLayout
    private lateinit var edittextAtasnama: TextInputEditText
    private lateinit var btnCheckout: Button

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private lateinit var cartViewModel: CartViewModel
    private lateinit var db: FirebaseFirestore

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        spinnerPayment = findViewById(R.id.spinner_payment)
        tvTitleRekening = findViewById(R.id.tv_title_rekening)
        textLayoutRekening = findViewById(R.id.text_layout_rekening)
        edittextRekening = findViewById(R.id.edittext_rekening)
        tvTitleAtasnama = findViewById(R.id.tv_title_atasnama)
        textLayoutAtasnama = findViewById(R.id.text_layout_atasnama)
        edittextAtasnama = findViewById(R.id.edittext_atasnama)
        btnCheckout = findViewById(R.id.btn_checkout)

        accountSessionPreferences = AccountSessionPreferences(this)
        user = accountSessionPreferences.idUser

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        db = Firebase.firestore

        setSpinnerPayment()

        btnCheckout.setOnClickListener {
            if (payment == "transfer" && edittextRekening.text.toString() == "") {
                edittextRekening.setError("Nomor rekening harus diisi!")
            } else if (payment == "transfer" && edittextAtasnama.text.toString() == "") {
                edittextAtasnama.setError("Atas nama rekening harus diisi!")
            } else {
                if (payment == "transfer") {
                    noRekening = edittextRekening.text.toString().toLong()
                    atasNama = edittextAtasnama.text.toString()
                }

                //set alert dialog builder
                builder = AlertDialog.Builder(this)

                //set title for alert dialog
                builder.setTitle("Confirm Checkout")

                //set message for alert dialog
                builder.setMessage("Lanjutkan checkout pesanan?")

                //set positive and negative button
                builder.apply {
                    setPositiveButton("Yes") { dialogInterface, i ->
                        // Upload Cart to Orders Database
                        // Rental
                        cartViewModel.getRentalList.observe(this@CheckoutActivity, Observer {

                            val listRental: MutableList<Rental> = ArrayList()

                            // Count Item for Current User
                            for (item in it) {
                                if (item.user == user) {
                                    listRental.add(item)
                                }
                            }

                            if (listRental.size > 0) {
                                for (item in listRental) {
                                    val itemRental = hashMapOf(
                                        "url_identitas" to item.url_identitas,
                                        "product" to item.product,
                                        "tgl_peminjaman" to item.tgl_peminjaman,
                                        "quantity" to item.quantity,
                                        "address" to item.address,
                                        "order_date" to System.currentTimeMillis(),
                                        "organisasi" to item.organisasi,
                                        "lama_peminjaman" to item.lama_peminjaman,
                                        "antar" to item.antar,
                                        "price" to item.price,
                                        "tgl_pengembalian" to item.tgl_pengembalian,
                                        "payment" to payment,
                                        "user" to item.user,
                                        "status" to 0,
                                        "rekening" to noRekening,
                                        "atas_nama" to atasNama
                                    )

                                    db.collection("orders_rental").add(itemRental).addOnSuccessListener {
                                        db.collection("products").document(item.product.toString())
                                            .update("stock", FieldValue.increment(-1))
                                    }
                                    cartViewModel.deleteRental(item)
                                }
                            }
                        })

                        // Desain
                        cartViewModel.getDesainList.observe(this@CheckoutActivity, Observer {

                            val listDesain: MutableList<Desain> = ArrayList()

                            // Count Item for Current User
                            for (item in it) {
                                if (item.user == user) {
                                    listDesain.add(item)
                                }
                            }

                            if (listDesain.size > 0) {
                                for (item in listDesain) {
                                    val itemDesain = hashMapOf(
                                        "deskripsi_desain" to item.deskripsi_desain,
                                        "email_pengiriman" to item.email_pengiriman,
                                        "order_date"  to System.currentTimeMillis(),
                                        "organisasi" to item.organisasi,
                                        "payment" to payment,
                                        "price" to item.price,
                                        "product" to item.product,
                                        "status" to 0,
                                        "url_file_pendukung" to item.url_file_pendukung,
                                        "user" to item.user,
                                        "waktu_pengerjaan" to item.waktu_pengerjaan,
                                        "rekening" to noRekening,
                                        "atas_nama" to atasNama
                                    )

                                    db.collection("orders_desain").add(itemDesain)
                                    cartViewModel.deleteDesain(item)
                                }
                            }
                        })
                        cartViewModel.getCetakList.observe(this@CheckoutActivity, Observer {

                            val listCetak: MutableList<Cetak> = ArrayList()

                            // Count Item for Current User
                            for (item in it) {
                                if (item.user == user) {
                                    listCetak.add(item)
                                }
                            }

                            if (listCetak.size > 0) {
                                for (item in listCetak) {
                                    val itemCetak = hashMapOf(
                                        "order_date" to System.currentTimeMillis(),
                                        "organisasi" to item.organisasi,
                                        "payment" to payment,
                                        "price" to item.price,
                                        "product" to item.product,
                                        "quantity" to item.quantity,
                                        "status" to 0,
                                        "url_file_desain" to item.url_file_desain,
                                        "user" to item.user,
                                        "rekening" to noRekening,
                                        "atas_nama" to atasNama
                                    )

                                    db.collection("orders_cetak").add(itemCetak)
                                    cartViewModel.deleteCetak(item)
                                }
                            }
                        })
                        cartViewModel.getInstallList.observe(this@CheckoutActivity, Observer {

                            val listInstall: MutableList<Install> = ArrayList()

                            // Count Item for Current User
                            for (item in it) {
                                if (item.user == user) {
                                    listInstall.add(item)
                                }
                            }

                            if (listInstall.size > 0) {
                                for (item in listInstall) {
                                    val itemInstall = hashMapOf(
                                        "address" to item.address,
                                        "antar" to item.antar,
                                        "catatan" to item.catatan,
                                        "game" to item.game,
                                        "isi_game" to item.isi_game,
                                        "isi_os" to item.isi_os,
                                        "isi_software_pc" to item.isi_software_pc,
                                        "order_date" to System.currentTimeMillis(),
                                        "os" to item.os,
                                        "os_add_on" to item.os_add_on,
                                        "payment" to payment,
                                        "price" to item.price,
                                        "product" to item.product,
                                        "software_pc" to item.software_pc,
                                        "status" to 0,
                                        "user" to item.user,
                                        "rekening" to noRekening,
                                        "atas_nama" to atasNama
                                    )

                                    db.collection("orders_install").add(itemInstall)
                                    cartViewModel.deleteInstall(item)
                                }
                            }
                        })

                        val intentCheckout = Intent()
                        intentCheckout.putExtra("IS_CHECKOUT", true)
                        setResult(RESULT_OK, intentCheckout)
                        showToast()
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

    private fun setSpinnerPayment() {
        val paymentList = resources.getStringArray(R.array.payment_type)
        spinnerPayment.adapter = ArrayAdapter(this, R.layout.item_spinner, paymentList)
        spinnerPayment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        payment = "cod"
                        tvTitleRekening.visibility = View.GONE
                        textLayoutRekening.visibility = View.GONE
                        tvTitleAtasnama.visibility = View.GONE
                        textLayoutAtasnama.visibility = View.GONE
                    }
                    1 -> {
                        payment = "transfer"
                        tvTitleRekening.visibility = View.VISIBLE
                        textLayoutRekening.visibility = View.VISIBLE
                        tvTitleAtasnama.visibility = View.VISIBLE
                        textLayoutAtasnama.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun showToast() {
        val toastCheckout = Toast(this)
        val toastView = layoutInflater.inflate(R.layout.toast_checkout, null)

        toastCheckout.view = toastView

        toastCheckout.duration = Toast.LENGTH_LONG
        toastCheckout.setGravity(Gravity.CENTER, 0, 0)
        toastCheckout.show()
    }
}