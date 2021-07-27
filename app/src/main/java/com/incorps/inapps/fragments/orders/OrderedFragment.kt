package com.incorps.inapps.fragments.orders

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.OrderDetailDesain
import com.incorps.inapps.OrderDetailRental
import com.incorps.inapps.R
import com.incorps.inapps.adapter.OrdersDesainAdapter
import com.incorps.inapps.adapter.OrdersRentalAdapter
import com.incorps.inapps.model.OrdersDesain
import com.incorps.inapps.model.OrdersRental
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.utils.Tools
import java.util.ArrayList

class OrderedFragment : Fragment() {

    private lateinit var rvOrderedRental: RecyclerView
    private lateinit var rvOrderedDesain: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var db: FirebaseFirestore
    private lateinit var accountSessionPreferences: AccountSessionPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ordered, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvOrderedRental = view.findViewById(R.id.rv_ordered_rental)
        rvOrderedDesain = view.findViewById(R.id.rv_ordered_desain)
        progressBar = view.findViewById(R.id.progress_wait)

        db = Firebase.firestore
        accountSessionPreferences = AccountSessionPreferences(requireContext())

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
                        document.data["product"] as Long,
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
                }

                // Show Recycler View
                rvOrderedRental.layoutManager = LinearLayoutManager(context)
                val ordersRentalAdapter = OrdersRentalAdapter(rentalList)
                rvOrderedRental.adapter = ordersRentalAdapter

                ordersRentalAdapter.setOnItemClickCallback(object : OrdersRentalAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: OrdersRental) {
                        val intentOrderDetailRental = Intent(context, OrderDetailRental::class.java)
                        intentOrderDetailRental.putExtra("order_rental", data)
                        startActivity(intentOrderDetailRental)
                    }
                })
            }
            .addOnFailureListener { exception ->
                FirebaseCrashlytics.getInstance().recordException(exception)
                Tools.showCustomToastFailed(requireContext(), layoutInflater, resources, exception.message.toString())
            }

        val desainList: MutableList<OrdersDesain> = ArrayList<OrdersDesain>()

        db.collection("orders_desain")
            .whereEqualTo("user", accountSessionPreferences.idUser)
            .whereEqualTo("status", 0)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val orderDesain = OrdersDesain(
                        document.id,
                        document.data["deskripsi_desain"] as String,
                        document.data["email_pengiriman"] as String,
                        document.data["organisasi"] as String,
                        document.data["payment"] as String,
                        document.data["price"] as Long,
                        document.data["product"] as Long,
                        document.data["status"] as Long,
                        document.data["url_file_pendukung"] as String,
                        document.data["user"] as String,
                        document.data["waktu_pengerjaan"] as Long,
                    )
                    desainList.add(orderDesain)
                }

                // Show Recycler View
                rvOrderedDesain.layoutManager = LinearLayoutManager(context)
                val ordersDesainAdapter = OrdersDesainAdapter(desainList)
                rvOrderedDesain.adapter = ordersDesainAdapter

                ordersDesainAdapter.setOnItemClickCallback(object : OrdersDesainAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: OrdersDesain) {
                        val intentOrderDetailDesain = Intent(context, OrderDetailDesain::class.java)
                        startActivity(intentOrderDetailDesain)
                    }
                })
            }
            .addOnFailureListener { exception ->
                FirebaseCrashlytics.getInstance().recordException(exception)
                Tools.showCustomToastFailed(requireContext(), layoutInflater, resources, exception.message.toString())
            }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK)) {
//            val ft = parentFragmentManager.beginTransaction()
//            ft.detach(OrderedFragment()).attach(OrderedFragment()).commit()
//        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE)
        } else {
            progressBar.setVisibility(View.GONE)
        }
    }
}