package com.incorps.inapps

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CheckoutActivity : AppCompatActivity() {
    private var payment = ""

    private lateinit var spinnerPayment: Spinner
    private lateinit var tvTitleRekening: TextView
    private lateinit var textLayoutRekening: TextInputLayout
    private lateinit var edittextRekening: TextInputEditText
    private lateinit var tvTitleAtasnama: TextView
    private lateinit var textLayoutAtasnama: TextInputLayout
    private lateinit var edittextAtasnama: TextInputEditText
    private lateinit var btnCheckout: Button

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

        setSpinnerPayment()

        btnCheckout.setOnClickListener {
            if (payment == "transfer" && edittextRekening.text.toString() == "") {
                edittextRekening.setError("Nomor rekening harus diisi!")
            } else if (payment == "transfer" && edittextAtasnama.text.toString() == "") {
                edittextAtasnama.setError("Atas nama rekening harus diisi!")
            } else {
                //set alert dialog builder
                builder = AlertDialog.Builder(this)

                //set title for alert dialog
                builder.setTitle("Confirm Checkout")

                //set message for alert dialog
                builder.setMessage("Lanjutkan checkout pesanan?")

                //set positive and negative button
                builder.apply {
                    setPositiveButton("Yes") { dialogInterface, i ->
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