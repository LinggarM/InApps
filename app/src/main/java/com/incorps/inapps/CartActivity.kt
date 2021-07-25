package com.incorps.inapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView

class CartActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var layoutEmpty: LinearLayout
    private lateinit var svCart: ScrollView
    private lateinit var imgBack: ImageView
    private lateinit var btnBack: Button
    private lateinit var btnCheckout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        layoutEmpty = findViewById(R.id.layout_empty)
        svCart = findViewById(R.id.sv_cart)
        imgBack = findViewById(R.id.img_back_button)
        btnBack = findViewById(R.id.btn_back_to_menu)
        btnCheckout = findViewById(R.id.btn_checkout)

        imgBack.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        btnCheckout.setOnClickListener(this)

        if (!isCartEmpty()) {
            layoutEmpty.visibility = View.GONE
            svCart.visibility = View.VISIBLE
            btnCheckout.visibility = View.VISIBLE
        }
    }

    private fun isCartEmpty(): Boolean {
        return true
    }

    override fun onClick(view: View?) {
        when(view) {
            imgBack -> {
                finish()
            }
            btnBack -> {
                finish()
            }
            btnCheckout -> {
                startActivity(Intent(this, CheckoutActivity::class.java))
            }
        }
    }
}