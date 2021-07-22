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

class CetakActivity : AppCompatActivity() {

    private lateinit var imgBack: ImageView
    private lateinit var rvCetak: RecyclerView
    private lateinit var cetakList: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cetak)

        imgBack = findViewById(R.id.img_back_button)
        rvCetak = findViewById(R.id.rv_cetak)

        // Set Recycler View
        cetakList = DataGenerator.getCetakProduct(this)
        showRecyclerView()

        imgBack.setOnClickListener {
            finish()
        }
    }

    private fun showRecyclerView() {
        rvCetak.layoutManager = GridLayoutManager(this, 2)
        rvCetak.addItemDecoration(SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_small)))
        val productAdapter = ProductGridAdapter(cetakList)
        rvCetak.adapter = productAdapter

        productAdapter.setOnItemClickCallback(object : ProductGridAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Product) {
                showSelectedProduct(data)
            }
        })
    }

    private fun showSelectedProduct(product: Product) {
        val className = "${resources.getString(R.string.cetak_activity)}${product.activity}"
        try {
            val productClass = Class.forName(className)
            startActivity(Intent(this, productClass))
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
}