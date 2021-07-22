package com.incorps.inapps.productsactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.incorps.inapps.R
import com.incorps.inapps.adapter.ProductGridAdapter
import com.incorps.inapps.helper.DataGenerator
import com.incorps.inapps.model.Product
import com.incorps.inapps.rest.SpacingItemDecoration

class RentalActivity : AppCompatActivity() {

    private lateinit var imgBack: ImageView
    private lateinit var rvInstall: RecyclerView
    private lateinit var rentalList: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental)

        imgBack = findViewById(R.id.img_back_button)
        rvInstall = findViewById(R.id.rv_rental)

        // Set Recycler View
        rentalList = DataGenerator.getRentalProduct(this)
        showRecyclerView()

        imgBack.setOnClickListener {
            finish()
        }
    }

    private fun showRecyclerView() {
        rvInstall.layoutManager = GridLayoutManager(this, 2)
        rvInstall.addItemDecoration(SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_small)))
        val productAdapter = ProductGridAdapter(rentalList)
        rvInstall.adapter = productAdapter

        productAdapter.setOnItemClickCallback(object : ProductGridAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Product) {
                showSelectedProduct(data)
            }
        })
    }

    private fun showSelectedProduct(product: Product) {
        val className = "${resources.getString(R.string.rental_activity)}${product.activity}"
        try {
            val productClass = Class.forName(className)
            startActivity(Intent(this, productClass))
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
}