package com.incorps.inapps.fragments.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.R
import com.incorps.inapps.adapter.OrdersRentalAdapter
import com.incorps.inapps.model.OrdersRental
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.utils.DataGenerator
import com.incorps.inapps.utils.Tools
import java.util.ArrayList

class OrderedFragment : Fragment() {

    private lateinit var rvOrdered: RecyclerView
    private lateinit var rentalList: List<OrdersRental>
    private lateinit var tvTest: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ordered, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvOrdered = view.findViewById(R.id.rv_ordered)
        tvTest = view.findViewById(R.id.tv_test)

//        rentalList = DataGenerator.getRentalOrdered(requireContext())
//        var textPrint = ""
//        for (rental in rentalList) {
//            textPrint = textPrint + rental.product + rental.tgl_peminjaman.toString() + rental.price.toString() + rental.quantity.toString()
//        }
//        tvTest.text = textPrint

        val db: FirebaseFirestore = Firebase.firestore
        val accountSessionPreferences = AccountSessionPreferences(requireContext())

        val rentalList: MutableList<OrdersRental> = ArrayList<OrdersRental>()

        db.collection("orders_rental")
            .whereEqualTo("user", accountSessionPreferences.idUser)
            .whereEqualTo("status", 0)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val orderRental = OrdersRental(
                        document.id,
                        document.data["url_identitas"] as String,
                        document.data["product"] as String,
                        document.data["tgl_peminjaman"] as Long,
                        document.data["quantity"] as Long,
                        document.data["address"] as String,
                        document.data["organisasi"] as String,
                        document.data["lama_peminjaman"] as Long,
                        document.data["antar"] as Boolean,
                        document.data["price"] as Long,
                        document.data["tgl_pengembalian"] as Long,
                        document.data["payment"] as String,
                        document.data["user"] as String,
                        document.data["status"] as Long
                    )
                    rentalList.add(orderRental)
//                    val rentalProduct = document.toObject<OrdersRental>()
//                    rentalList.add(rentalProduct)

//                    var textPrint = ""
//                    textPrint += document.id
//                    for (data in document.data) {
//                        when (data.value) {
//                            is String -> {
//                                textPrint += "${data.key} : String : ${data.value}\n, "
//                            }
//                            is Int -> {
//                                textPrint += "${data.key} : Int : ${data.value}\n, "
//                            }
//                            is Long -> {
//                                textPrint += "${data.key} : Long : ${data.value}\n, "
//                            }
//                            is Boolean -> {
//                                textPrint += "${data.key} : Boolean : ${data.value}\n, "
//                            }
//                        }
//                    }
//                    tvTest.text = textPrint
                }

//                tvTest.text = "$berhasil : ${rentalList.size.toString()}"
//
//                for (rental in rentalList) {
//                    var textPrint = ""
//                    textPrint = rental.product + rental.tgl_peminjaman.toString() + rental.price.toString() + rental.quantity.toString()
//                    tvTest.text = textPrint
//                }
                try {
                    rvOrdered.layoutManager = LinearLayoutManager(context)
                    val ordersRentalAdapter = OrdersRentalAdapter(rentalList)
                    rvOrdered.adapter = ordersRentalAdapter
                } catch (e: Exception) {
                    tvTest.text = e.toString()
                }
            }
            .addOnFailureListener { exception ->
            }

//        // Set Recycler View
//        rentalList = DataGenerator.getRentalOrdered(requireContext())
//        showRecyclerView()
    }

    private fun showRecyclerView() {
        rvOrdered.layoutManager = LinearLayoutManager(context)
        val ordersRentalAdapter = OrdersRentalAdapter(rentalList)
        rvOrdered.adapter = ordersRentalAdapter
    }
}