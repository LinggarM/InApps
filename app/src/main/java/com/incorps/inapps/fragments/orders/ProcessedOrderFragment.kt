package com.incorps.inapps.fragments.orders

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.*
import com.incorps.inapps.adapter.OrdersCetakAdapter
import com.incorps.inapps.adapter.OrdersDesainAdapter
import com.incorps.inapps.adapter.OrdersInstallAdapter
import com.incorps.inapps.adapter.OrdersRentalAdapter
import com.incorps.inapps.model.OrdersCetak
import com.incorps.inapps.model.OrdersDesain
import com.incorps.inapps.model.OrdersInstall
import com.incorps.inapps.model.OrdersRental
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.utils.Tools
import java.util.ArrayList

class ProcessedOrderFragment : Fragment() {
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var progressWait: ProgressBar
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var btnCart: Button

    private lateinit var tvRental: TextView
    private lateinit var tvDesain: TextView
    private lateinit var tvCetak: TextView
    private lateinit var tvInstall: TextView

    private lateinit var rvProcessedRental: RecyclerView
    private lateinit var rvProcessedDesain: RecyclerView
    private lateinit var rvProcessedCetak: RecyclerView
    private lateinit var rvProcessedInstall: RecyclerView

    private lateinit var db: FirebaseFirestore
    private lateinit var accountSessionPreferences: AccountSessionPreferences

    private var isEmpty: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_processed_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        progressWait = view.findViewById(R.id.progress_wait)
        layoutEmpty = view.findViewById(R.id.layout_empty)
        btnCart = view.findViewById(R.id.btn_back_to_menu)

        tvRental = view.findViewById(R.id.tv_rental)
        tvDesain = view.findViewById(R.id.tv_desain)
        tvCetak = view.findViewById(R.id.tv_cetak)
        tvInstall = view.findViewById(R.id.tv_install)

        rvProcessedRental = view.findViewById(R.id.rv_processed_rental)
        rvProcessedDesain = view.findViewById(R.id.rv_processed_desain)
        rvProcessedCetak = view.findViewById(R.id.rv_processed_cetak)
        rvProcessedInstall = view.findViewById(R.id.rv_processed_install)

        db = Firebase.firestore
        accountSessionPreferences = AccountSessionPreferences(requireContext())

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
        }

        btnCart.setOnClickListener {
            startActivity(Intent(context, CartActivity::class.java))
        }

        // RecyclerView Rental
        val rentalList: MutableList<OrdersRental> = ArrayList<OrdersRental>()

        db.collection("orders_rental")
            .whereEqualTo("user", accountSessionPreferences.idUser)
            .whereEqualTo("status", 1)
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
                        document.data["order_date"] as Long,
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

                progressWait.visibility = View.GONE

                if (rentalList.size > 0) {
                    isEmpty = false
                    layoutEmpty.visibility = View.GONE

                    // Hide Layout Empty
                    swipeRefresh.visibility = View.VISIBLE
                    tvRental.visibility = View.VISIBLE
                    rvProcessedRental.visibility = View.VISIBLE

                    // Show Recycler View
                    rvProcessedRental.layoutManager = LinearLayoutManager(context)
                    val ordersRentalAdapter = OrdersRentalAdapter(rentalList)
                    rvProcessedRental.adapter = ordersRentalAdapter

                    ordersRentalAdapter.setOnItemClickCallback(object :
                        OrdersRentalAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: OrdersRental) {
                            val intentOrderDetailRental = Intent(context, OrderDetailRental::class.java)
                            intentOrderDetailRental.putExtra("order_rental", data)
                            startActivity(intentOrderDetailRental)
                        }
                    })
                }
            }
            .addOnFailureListener { exception ->
                FirebaseCrashlytics.getInstance().recordException(exception)
                Tools.showCustomToastFailed(
                    requireContext(),
                    layoutInflater,
                    resources,
                    exception.message.toString()
                )
            }

        // RecyclerView Desain
        val desainList: MutableList<OrdersDesain> = ArrayList<OrdersDesain>()

        db.collection("orders_desain")
            .whereEqualTo("user", accountSessionPreferences.idUser)
            .whereEqualTo("status", 1)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val orderDesain = OrdersDesain(
                        document.id,
                        document.data["deskripsi_desain"] as String,
                        document.data["email_pengiriman"] as String,
                        document.data["order_date"] as Long,
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

                progressWait.visibility = View.GONE

                if (desainList.size > 0) {
                    isEmpty = false
                    layoutEmpty.visibility = View.GONE

                    // Hide Layout Empty
                    swipeRefresh.visibility = View.VISIBLE
                    tvDesain.visibility = View.VISIBLE
                    rvProcessedDesain.visibility = View.VISIBLE

                    // Show Recycler View
                    rvProcessedDesain.layoutManager = LinearLayoutManager(context)
                    val ordersDesainAdapter = OrdersDesainAdapter(desainList)
                    rvProcessedDesain.adapter = ordersDesainAdapter

                    ordersDesainAdapter.setOnItemClickCallback(object :
                        OrdersDesainAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: OrdersDesain) {
                            val intentOrderDetailDesain = Intent(context, OrderDetailDesain::class.java)
                            intentOrderDetailDesain.putExtra("order_desain", data)
                            startActivity(intentOrderDetailDesain)
                        }
                    })
                }
            }
            .addOnFailureListener { exception ->
                FirebaseCrashlytics.getInstance().recordException(exception)
                Tools.showCustomToastFailed(
                    requireContext(),
                    layoutInflater,
                    resources,
                    exception.message.toString()
                )
            }

        // RecyclerView Cetak
        val cetakList: MutableList<OrdersCetak> = ArrayList<OrdersCetak>()

        db.collection("orders_cetak")
            .whereEqualTo("user", accountSessionPreferences.idUser)
            .whereEqualTo("status", 1)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val ordersCetak = OrdersCetak(
                        document.id,
                        document.data["order_date"] as Long,
                        document.data["organisasi"] as String,
                        document.data["payment"] as String,
                        document.data["price"] as Long,
                        document.data["product"] as Long,
                        document.data["quantity"] as Long,
                        document.data["status"] as Long,
                        document.data["url_file_desain"] as String,
                        document.data["user"] as String,
                    )
                    cetakList.add(ordersCetak)
                }

                progressWait.visibility = View.GONE

                if (cetakList.size > 0) {
                    isEmpty = false
                    layoutEmpty.visibility = View.GONE

                    // Hide Layout Empty
                    swipeRefresh.visibility = View.VISIBLE
                    tvCetak.visibility = View.VISIBLE
                    rvProcessedCetak.visibility = View.VISIBLE

                    // Show Recycler View
                    rvProcessedCetak.layoutManager = LinearLayoutManager(context)
                    val ordersCetakAdapter = OrdersCetakAdapter(cetakList)
                    rvProcessedCetak.adapter = ordersCetakAdapter

                    ordersCetakAdapter.setOnItemClickCallback(object :
                        OrdersCetakAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: OrdersCetak) {
                            val intentOrderDetailCetak = Intent(context, OrderDetailCetak::class.java)
                            intentOrderDetailCetak.putExtra("order_cetak", data)
                            startActivity(intentOrderDetailCetak)
                        }
                    })
                }

                if (isEmpty) {
                    layoutEmpty.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { exception ->
                FirebaseCrashlytics.getInstance().recordException(exception)
                Tools.showCustomToastFailed(
                    requireContext(),
                    layoutInflater,
                    resources,
                    exception.message.toString()
                )
            }

        // RecyclerView Install
        val installList: MutableList<OrdersInstall> = ArrayList<OrdersInstall>()

        db.collection("orders_install")
            .whereEqualTo("user", accountSessionPreferences.idUser)
            .whereEqualTo("status", 1)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val ordersInstall = OrdersInstall(
                        document.id,
                        document.data["address"] as String,
                        document.data["antar"] as Boolean,
                        document.data["catatan"] as String,
                        document.data["game"] as Boolean,
                        document.data["isi_game"] as String,
                        document.data["isi_os"] as String,
                        document.data["isi_software_pc"] as String,
                        document.data["order_date"] as Long,
                        document.data["os"] as Boolean,
                        document.data["os_add_on"] as Boolean,
                        document.data["payment"] as String,
                        document.data["price"] as Long,
                        document.data["product"] as Long,
                        document.data["software_pc"] as Boolean,
                        document.data["status"] as Long,
                        document.data["user"] as String,
                    )
                    installList.add(ordersInstall)
                }

                progressWait.visibility = View.GONE

                if (installList.size > 0) {
                    isEmpty = false
                    layoutEmpty.visibility = View.GONE

                    // Hide Layout Empty
                    swipeRefresh.visibility = View.VISIBLE
                    tvInstall.visibility = View.VISIBLE
                    rvProcessedInstall.visibility = View.VISIBLE

                    // Show Recycler View
                    rvProcessedInstall.layoutManager = LinearLayoutManager(context)
                    val ordersInstallAdapeter = OrdersInstallAdapter(installList)
                    rvProcessedInstall.adapter = ordersInstallAdapeter

                    ordersInstallAdapeter.setOnItemClickCallback(object :
                        OrdersInstallAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: OrdersInstall) {
                            val intentOrderDetailInstall = Intent(context, OrderDetailInstall::class.java)
                            intentOrderDetailInstall.putExtra("order_install", data)
                            startActivity(intentOrderDetailInstall)
                        }
                    })
                }

                if (isEmpty) {
                    layoutEmpty.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { exception ->
                FirebaseCrashlytics.getInstance().recordException(exception)
                Tools.showCustomToastFailed(
                    requireContext(),
                    layoutInflater,
                    resources,
                    exception.message.toString()
                )
            }
    }
}