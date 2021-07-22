package com.incorps.inapps.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.card.MaterialCardView
import com.incorps.inapps.CartActivity
import com.incorps.inapps.R
import com.incorps.inapps.productsactivity.CetakActivity
import com.incorps.inapps.productsactivity.DesainActivity
import com.incorps.inapps.productsactivity.InstallActivity
import com.incorps.inapps.productsactivity.RentalActivity

class HomeFragment : Fragment() {
    private lateinit var imgCart: ImageView
    private lateinit var tvWelcome: TextView

    private lateinit var btnRental: MaterialCardView
    private lateinit var btnDesain: MaterialCardView
    private lateinit var btnCetak: MaterialCardView
    private lateinit var btnInstall: MaterialCardView

    private var personName: String = ""
    private var personEmail: String = ""
    private var personId: String = ""
    private var personPhoto: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvWelcome = view.findViewById(R.id.tv_welcome_username)
        imgCart = view.findViewById(R.id.img_cart)

        btnRental = view.findViewById(R.id.btn_rental)
        btnDesain = view.findViewById(R.id.btn_desain)
        btnCetak = view.findViewById(R.id.btn_cetak)
        btnInstall = view.findViewById(R.id.btn_install)

        // Data from Google Sign In
        val acct = GoogleSignIn.getLastSignedInAccount(context)
        if (acct != null) {
            personName = acct.displayName.toString()
            personEmail = acct.email.toString()
            personId = acct.id.toString()
            personPhoto = acct.photoUrl
        }

        // Welcome Name
        var firstWord = personName
        if (firstWord.contains(" ")) {
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
}