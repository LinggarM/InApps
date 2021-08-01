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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.adapter.*
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.room.*
import com.incorps.inapps.utils.Tools

class CartActivity : AppCompatActivity(), View.OnClickListener {

    private var isStockKurang: Boolean = false
    private var qtyMessage: String = ""

    private var user: String = ""
    private var totalPrice: Int = 0
    private var priceRental: Int = 0
    private var priceDesain: Int = 0
    private var priceCetak: Int = 0
    private var priceInstall: Int = 0
    private var isEmpty: Boolean = true
    private var isCheckout: Boolean = false
    private var CHECKOUT_CODE: Int = 1

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private lateinit var cartViewModel : CartViewModel
    private lateinit var db: FirebaseFirestore

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

        accountSessionPreferences = AccountSessionPreferences(this)
        user = accountSessionPreferences.idUser

        db = Firebase.firestore

        imgBack.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        btnCheckout.setOnClickListener(this)

        // Rental
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.getRentalList.observe(this, Observer {

            isStockKurang = false

            var listRental: MutableList<Rental> = ArrayList()

            // Count Item for Current User
            var itemRentalCounter = 0
            for (item in it) {
                if (item.user == user) {
                    itemRentalCounter += 1
                    listRental.add(item)
                }
            }

            // Show Recycler View
            rvCartRental.visibility = View.VISIBLE
            rvCartRental.layoutManager = LinearLayoutManager(this)
            val cartRentalAdapter = CartRentalAdapter()
            cartRentalAdapter.setData(listRental)
            cartRentalAdapter.setViewModel(cartViewModel)
            cartRentalAdapter.setContext(this)
            rvCartRental.adapter = cartRentalAdapter

            if (itemRentalCounter > 0) {
                // Hide Layout Empty
                isEmpty = false
                layoutEmpty.visibility = View.GONE
                svCart.visibility = View.VISIBLE
                layoutBottom.visibility = View.VISIBLE

                tvRentalin.visibility = View.VISIBLE
                rvCartRental.visibility = View.VISIBLE

                // Update Price
                priceRental = 0
                for (i in listRental) {
                    priceRental += i.price

                    // Check Quantity
                    db.collection("products").document(i.product.toString()).get().addOnSuccessListener { it ->
                        it.let {
                            val stock = it.get("stock") as Long
                            if (stock < i.quantity) {
                                isStockKurang = true
                                qtyMessage = "Stok ${Tools.getProductNameById(i.product)} tinggal $stock"
                            }
                        }
                    }
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

            var listDesain: MutableList<Desain> = ArrayList()

            // Count Item for Current User
            var itemDesainCounter = 0
            for (item in it) {
                if (item.user == user) {
                    itemDesainCounter += 1
                    listDesain.add(item)
                }
            }

            // Show Recycler View
            rvCartDesain.visibility = View.VISIBLE
            rvCartDesain.layoutManager = LinearLayoutManager(this)
            val cartDesainAdapter = CartDesainAdapter()
            cartDesainAdapter.setData(listDesain)
            cartDesainAdapter.setViewModel(cartViewModel)
            cartDesainAdapter.setContext(this)
            rvCartDesain.adapter = cartDesainAdapter

            if (itemDesainCounter > 0) {
                // Hide Layout Empty
                isEmpty = false
                layoutEmpty.visibility = View.GONE
                svCart.visibility = View.VISIBLE
                layoutBottom.visibility = View.VISIBLE

                tvDesainin.visibility = View.VISIBLE
                rvCartDesain.visibility = View.VISIBLE

                // Update Price
                priceDesain = 0
                for (i in listDesain) {
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

            var listCetak: MutableList<Cetak> = ArrayList()

            // Count Item for Current User
            var itemCetakCounter = 0
            for (item in it) {
                if (item.user == user) {
                    itemCetakCounter += 1
                    listCetak.add(item)
                }
            }

            // Show Recycler View
            rvCartCetak.visibility = View.VISIBLE
            rvCartCetak.layoutManager = LinearLayoutManager(this)
            val cartCetakAdapter = CartCetakAdapter()
            cartCetakAdapter.setData(listCetak)
            cartCetakAdapter.setViewModel(cartViewModel)
            cartCetakAdapter.setContext(this)
            rvCartCetak.adapter = cartCetakAdapter

            if (itemCetakCounter > 0) {
                // Hide Layout Empty
                isEmpty = false
                layoutEmpty.visibility = View.GONE
                svCart.visibility = View.VISIBLE
                layoutBottom.visibility = View.VISIBLE

                tvCetakin.visibility = View.VISIBLE
                rvCartCetak.visibility = View.VISIBLE

                // Update Price
                priceCetak = 0
                for (i in listCetak) {
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

            var listInstall: MutableList<Install> = ArrayList()

            // Count Item for Current User
            var itemInstallCounter = 0
            for (item in it) {
                if (item.user == user) {
                    itemInstallCounter += 1
                    listInstall.add(item)
                }
            }

            // Show Recycler View
            rvCartInstall.visibility = View.VISIBLE
            rvCartInstall.layoutManager = LinearLayoutManager(this)
            val cartInstallAdapter = CartInstallAdapter()
            cartInstallAdapter.setData(listInstall)
            cartInstallAdapter.setViewModel(cartViewModel)
            cartInstallAdapter.setContext(this)
            rvCartInstall.adapter = cartInstallAdapter

            if (itemInstallCounter > 0) {
                // Hide Layout Empty
                isEmpty = false
                layoutEmpty.visibility = View.GONE
                svCart.visibility = View.VISIBLE
                layoutBottom.visibility = View.VISIBLE

                tvInstallin.visibility = View.VISIBLE
                rvCartInstall.visibility = View.VISIBLE

                // Update Price
                priceInstall = 0
                for (i in listInstall) {
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
                if (totalPrice == 0) {
                    Tools.showCustomToastFailed(this, layoutInflater, resources, "Cart tidak boleh kosong!")
                } else if (isStockKurang) {
                    Tools.showCustomToastFailed(this, layoutInflater, resources, qtyMessage)
                } else {
                    val intentCheckout = Intent(this, CheckoutActivity::class.java)
                    startActivityForResult(intentCheckout, CHECKOUT_CODE)
                }
            }
        }
    }

    private fun updateTotalPrice() {
        totalPrice = priceRental + priceDesain + priceCetak + priceInstall
        tvPrice.text = Tools.getCurrencySeparator(totalPrice.toLong())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHECKOUT_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                isCheckout = data.getBooleanExtra("IS_CHECKOUT", false)
            }
            if (isCheckout) {
                finish()
            }
        }
    }
}