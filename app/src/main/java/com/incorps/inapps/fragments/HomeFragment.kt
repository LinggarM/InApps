package com.incorps.inapps.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.CartActivity
import com.incorps.inapps.R
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.productsactivity.CetakActivity
import com.incorps.inapps.productsactivity.DesainActivity
import com.incorps.inapps.productsactivity.InstallActivity
import com.incorps.inapps.productsactivity.RentalActivity
import com.incorps.inapps.room.CartViewModel
import com.incorps.inapps.utils.Tools

class HomeFragment : Fragment() {
    private var user: String = ""
    private var totalItemCart: Int = 0
    var itemRental: Int = 0
    var itemDesain: Int = 0
    var itemCetak: Int = 0
    var itemInstall: Int = 0

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private lateinit var cartViewModel : CartViewModel

    private lateinit var imgCart: ImageView
    private lateinit var tvCartCounter: TextView
    private lateinit var tvWelcome: TextView

    private lateinit var btnRental: MaterialCardView
    private lateinit var btnDesain: MaterialCardView
    private lateinit var btnCetak: MaterialCardView
    private lateinit var btnInstall: MaterialCardView

    private var personName: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvWelcome = view.findViewById(R.id.tv_welcome_username)
        imgCart = view.findViewById(R.id.img_cart)
        tvCartCounter = view.findViewById(R.id.tv_cart_counter)

        btnRental = view.findViewById(R.id.btn_rental)
        btnDesain = view.findViewById(R.id.btn_desain)
        btnCetak = view.findViewById(R.id.btn_cetak)
        btnInstall = view.findViewById(R.id.btn_install)

        accountSessionPreferences = AccountSessionPreferences(requireContext())
        user = accountSessionPreferences.idUser

        // Welcome Name
        personName = accountSessionPreferences.nameUser
        var firstWord = personName
        if (personName.contains(" ")) {
            firstWord = firstWord.substring(0, firstWord.indexOf(" "))
        }
        if (firstWord.length > 8) {
            firstWord = "Kak"
        }
        val welcomeName = "Hi, $firstWord"
        tvWelcome.text = welcomeName

        // Cart Icon Button
        imgCart.setOnClickListener {
            val intentCart = Intent(view.context, CartActivity::class.java)
            startActivity(intentCart)
        }

        // Cart Counter
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.getRentalList.observe(viewLifecycleOwner, Observer {
            var itemRentalCounter = 0
            for (item in it) {
                if (item.user == user) {
                    itemRentalCounter += 1
                }
            }
            itemRental = itemRentalCounter
            updateTotalItems()
        })
        cartViewModel.getDesainList.observe(viewLifecycleOwner, Observer {
            var itemDesainCounter = 0
            for (item in it) {
                if (item.user == user) {
                    itemDesainCounter += 1
                }
            }
            itemDesain = itemDesainCounter
            updateTotalItems()
        })
        cartViewModel.getCetakList.observe(viewLifecycleOwner, Observer {
            var itemCetakCounter = 0
            for (item in it) {
                if (item.user == user) {
                    itemCetakCounter += 1
                }
            }
            itemCetak = itemCetakCounter
            updateTotalItems()
        })
        cartViewModel.getInstallList.observe(viewLifecycleOwner, Observer {
            var itemInstallCounter = 0
            for (item in it) {
                if (item.user == user) {
                    itemInstallCounter += 1
                }
            }
            itemInstall = itemInstallCounter
            updateTotalItems()
        })


        // Product onClick Listener
        btnRental.setOnClickListener {
            startActivity(Intent(view.context, RentalActivity::class.java))
        }
        btnDesain.setOnClickListener {
            startActivity(Intent(view.context, DesainActivity::class.java))
        }
        btnCetak.setOnClickListener {
            startActivity(Intent(view.context, CetakActivity::class.java))
        }
        btnInstall.setOnClickListener {
            startActivity(Intent(view.context, InstallActivity::class.java))
        }
    }

    private fun updateTotalItems() {
        totalItemCart = itemRental + itemDesain + itemCetak + itemInstall
        tvCartCounter.text = totalItemCart.toString()
        if (totalItemCart == 0) {
            tvCartCounter.visibility = View.GONE
        } else {
            tvCartCounter.visibility = View.VISIBLE
        }
    }
}