package com.incorps.inapps.utils

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.R
import com.incorps.inapps.model.OrdersRental
import com.incorps.inapps.model.Product
import com.incorps.inapps.preferences.AccountSessionPreferences
import java.util.ArrayList

object DataGenerator {

    fun getRentalProduct(context: Context): List<Product> {

        val rentalList: MutableList<Product> = ArrayList<Product>()

        val imgProduct = context.resources.obtainTypedArray(R.array.rental_product_image)
        val titleProduct = context.resources.getStringArray(R.array.rental_product_title)
        val priceProduct = context.resources.getStringArray(R.array.rental_product_price)
        val activityProduct = context.resources.getStringArray(R.array.rental_product_activity)

        for (i in 0 until imgProduct.length()) {
            val product = Product(
                imgProduct.getResourceId(i, -1),
                titleProduct[i],
                priceProduct[i],
                activityProduct[i]
            )
            rentalList.add(product)
        }

        return rentalList
    }

    fun getDesainProduct(context: Context): List<Product> {

        val desainList: MutableList<Product> = ArrayList<Product>()

        val imgProduct = context.resources.obtainTypedArray(R.array.desain_product_image)
        val titleProduct = context.resources.getStringArray(R.array.desain_product_title)
        val priceProduct = context.resources.getStringArray(R.array.desain_product_price)
        val activityProduct = context.resources.getStringArray(R.array.desain_product_activity)

        for (i in 0 until imgProduct.length()) {
            val product = Product(
                imgProduct.getResourceId(i, -1),
                titleProduct[i],
                priceProduct[i],
                activityProduct[i]
            )
            desainList.add(product)
        }

        return desainList
    }

    fun getCetakProduct(context: Context): List<Product> {

        val cetakList: MutableList<Product> = ArrayList<Product>()

        val imgProduct = context.resources.obtainTypedArray(R.array.cetak_product_image)
        val titleProduct = context.resources.getStringArray(R.array.cetak_product_title)
        val priceProduct = context.resources.getStringArray(R.array.cetak_product_price)
        val activityProduct = context.resources.getStringArray(R.array.cetak_product_activity)

        for (i in 0 until imgProduct.length()) {
            val product = Product(
                imgProduct.getResourceId(i, -1),
                titleProduct[i],
                priceProduct[i],
                activityProduct[i]
            )
            cetakList.add(product)
        }

        return cetakList
    }
}