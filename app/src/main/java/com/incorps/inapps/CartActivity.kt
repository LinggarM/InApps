package com.incorps.inapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.incorps.inapps.adapter.*
import com.incorps.inapps.room.CartViewModel
import com.incorps.inapps.utils.Tools

class CartActivity : AppCompatActivity(), View.OnClickListener {

    private var totalPrice: Int = 0
    private var priceRental: Int = 0
    private var priceDesain: Int = 0
    private var priceCetak: Int = 0
    private var priceInstall: Int = 0
    private var isEmpty: Boolean = true
    private lateinit var cartViewModel : CartViewModel

    private lateinit var imgBack: ImageView
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var btnBack: Button
    private lateinit var svCart: NestedScrollView
    private lateinit var tvRentalin: TextView
    private lateinit var rvCartRental: RecyclerView
    private lateinit var tvDesainin: TextView
    private lateinit var rvCartDesain: RecyclerView
    private lateinit var tvCetakin: TextView
    private lateinit var rvCartCetak: RecyclerView
    private lateinit var tvInstallin: TextView
    private lateinit var rvCartInstall: RecyclerView
    private lateinit var layoutBottom: LinearLayout
    private lateinit var tvPrice: TextView
    private lateinit var btnCheckout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        imgBack = findViewById(R.id.img_back_button)
        layoutEmpty = findViewById(R.id.layout_empty)
        btnBack = findViewById(R.id.btn_back_to_menu)
        svCart = findViewById(R.id.sv_cart)
        tvRentalin = findViewById(R.id.tv_rentalin)
        rvCartRental = findViewById(R.id.rv_cart_rental)
        tvDesainin = findViewById(R.id.tv_desainin)
        rvCartDesain = findViewById(R.id.rv_cart_desain)
        tvCetakin = findViewById(R.id.tv_cetakin)
        rvCartCetak = findViewById(R.id.rv_cart_cetak)
        tvInstallin = findViewById(R.id.tv_installin)
        rvCartInstall = findViewById(R.id.rv_cart_install)
        layoutBottom = findViewById(R.id.layout_bottom)
        tvPrice = findViewById(R.id.tv_price)
        btnCheckout = findViewById(R.id.btn_checkout)

        imgBack.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        btnCheckout.setOnClickListener(this)

        // Rental
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.getRentalList.observe(this, Observer {

            // Show Recycler View
            rvCartRental.visibility = View.VISIBLE
            rvCartRental.layoutManager = LinearLayoutManager(this)
            val cartRentalAdapter = CartRentalAdapter()
            cartRentalAdapter.setData(it)
            cartRentalAdapter.setViewModel(cartViewModel)
            rvCartRental.adapter = cartRentalAdapter

            if (it.isNotEmpty()) {
                // Hide Layout Empty
                isEmpty = false
                layoutEmpty.visibility = View.GONE
                svCart.visibility = View.VISIBLE
                layoutBottom.visibility = View.VISIBLE

                tvRentalin.visibility = View.VISIBLE
                rvCartRental.visibility = View.VISIBLE

                // Update Price
                priceRental = 0
                for (i in it) {
                    priceRental += i.price
                }
            } else {
                tvRentalin.visibility = View.GONE
                rvCartRental.visibility = View.GONE

                isEmpty = true
                priceRental = 0
            }
            updateTotalPrice()
        })

        // Desain
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.getDesainList.observe(this, Observer {

            // Show Recycler View
            rvCartDesain.visibility = View.VISIBLE
            rvCartDesain.layoutManager = LinearLayoutManager(this)
            val cartDesainAdapter = CartDesainAdapter()
            cartDesainAdapter.setData(it)
            cartDesainAdapter.setViewModel(cartViewModel)
            rvCartDesain.adapter = cartDesainAdapter

            if (it.isNotEmpty()) {
                // Hide Layout Empty
                isEmpty = false
                layoutEmpty.visibility = View.GONE
                svCart.visibility = View.VISIBLE
                layoutBottom.visibility = View.VISIBLE

                tvDesainin.visibility = View.VISIBLE
                rvCartDesain.visibility = View.VISIBLE

                // Update Price
                priceDesain = 0
                for (i in it) {
                    priceDesain += i.price
                }
            } else {
                tvDesainin.visibility = View.GONE
                rvCartDesain.visibility = View.GONE

                isEmpty = true
                priceDesain = 0
            }
            updateTotalPrice()
        })

        // Cetak
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.getCetakList.observe(this, Observer {

            // Show Recycler View
            rvCartCetak.visibility = View.VISIBLE
            rvCartCetak.layoutManager = LinearLayoutManager(this)
            val cartCetakAdapter = CartCetakAdapter()
            cartCetakAdapter.setData(it)
            cartCetakAdapter.setViewModel(cartViewModel)
            rvCartCetak.adapter = cartCetakAdapter

            if (it.isNotEmpty()) {
                // Hide Layout Empty
                isEmpty = false
                layoutEmpty.visibility = View.GONE
                svCart.visibility = View.VISIBLE
                layoutBottom.visibility = View.VISIBLE

                tvCetakin.visibility = View.VISIBLE
                rvCartCetak.visibility = View.VISIBLE

                // Update Price
                priceCetak = 0
                for (i in it) {
                    priceCetak += i.price
                }
            } else {
                tvCetakin.visibility = View.GONE
                rvCartCetak.visibility = View.GONE

                isEmpty = true
                priceCetak = 0
            }
            updateTotalPrice()
        })

        // Install
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.getInstallList.observe(this, Observer {

            // Show Recycler View
            rvCartInstall.visibility = View.VISIBLE
            rvCartInstall.layoutManager = LinearLayoutManager(this)
            val cartInstallAdapter = CartInstallAdapter()
            cartInstallAdapter.setData(it)
            cartInstallAdapter.setViewModel(cartViewModel)
            rvCartInstall.adapter = cartInstallAdapter

            if (it.isNotEmpty()) {
                // Hide Layout Empty
                isEmpty = false
                layoutEmpty.visibility = View.GONE
                svCart.visibility = View.VISIBLE
                layoutBottom.visibility = View.VISIBLE

                tvInstallin.visibility = View.VISIBLE
                rvCartInstall.visibility = View.VISIBLE

                // Update Price
                priceInstall = 0
                for (i in it) {
                    priceInstall += i.price
                }
            } else {
                tvInstallin.visibility = View.GONE
                rvCartInstall.visibility = View.GONE

                isEmpty = true
                priceInstall = 0
            }
            updateTotalPrice()
        })
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

    private fun updateTotalPrice() {
        totalPrice = priceRental + priceDesain + priceCetak + priceInstall
        tvPrice.text = Tools.getCurrencySeparator(totalPrice.toLong())
    }
}