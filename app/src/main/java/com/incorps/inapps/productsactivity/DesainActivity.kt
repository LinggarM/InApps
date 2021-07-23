package com.incorps.inapps.productsactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.incorps.inapps.R
import com.incorps.inapps.adapter.ProductGridAdapter
import com.incorps.inapps.utils.DataGenerator
import com.incorps.inapps.model.Product
import com.incorps.inapps.utils.SpacingItemDecoration

class DesainActivity : AppCompatActivity() {

    private lateinit var imgBack: ImageView
    private lateinit var rvDesain: RecyclerView
    private lateinit var desainList: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desain)

        imgBack = findViewById(R.id.img_back_button)
        rvDesain = findViewById(R.id.rv_desain)

        // Set Recycler View
        desainList = DataGenerator.getDesainProduct(this)
        showRecyclerView()

        imgBack.setOnClickListener {
            finish()
        }
    }

    private fun showRecyclerView() {
        rvDesain.layoutManager = GridLayoutManager(this, 2)
        rvDesain.addItemDecoration(SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_small)))
        val productAdapter = ProductGridAdapter(desainList)
        rvDesain.adapter = productAdapter

        productAdapter.setOnItemClickCallback(object : ProductGridAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Product) {
                showSelectedProduct(data)
            }
        })
    }

    private fun showSelectedProduct(product: Product) {
        val className = "${resources.getString(R.string.desain_activity)}${product.activity}"
        try {
            val productClass = Class.forName(className)
            startActivity(Intent(this, productClass))
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
}