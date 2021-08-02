package com.incorps.inapps.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.EditProfileActivity
import com.incorps.inapps.KritikSaranActivity
import com.incorps.inapps.R
import com.incorps.inapps.SignInActivity
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.utils.Tools

class ProfileFragment : Fragment() {

    private lateinit var accountSessionPreferences: AccountSessionPreferences

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var imgProfile: ImageView
    private lateinit var btnTerms: MaterialCardView
    private lateinit var btnKritik: MaterialCardView
    private lateinit var btnRateApp: MaterialCardView
    private lateinit var btnAbout: MaterialCardView
    private lateinit var btnLogout: Button

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private lateinit var auth: FirebaseAuth

    private var personId: String = ""
    private var personEmail: String = ""
    private var personName: String = ""
    private var personPhone: String = ""
    private var personAddress: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvName = view.findViewById(R.id.tv_name)
        tvEmail = view.findViewById(R.id.tv_email)
        imgProfile = view.findViewById(R.id.img_profile)
        btnTerms = view.findViewById(R.id.btn_terms)
        btnKritik = view.findViewById(R.id.btn_kritik)
        btnRateApp = view.findViewById(R.id.btn_rate_app)
        btnAbout = view.findViewById(R.id.btn_about)
        btnLogout = view.findViewById(R.id.btn_logout)

        accountSessionPreferences = AccountSessionPreferences(requireContext())

        auth = Firebase.auth

        // Data from Shared Preferences
        if (accountSessionPreferences.isLogin) {
            personId = accountSessionPreferences.idUser
            personEmail = accountSessionPreferences.emailUser
            personName = accountSessionPreferences.nameUser
            personPhone = accountSessionPreferences.phoneUser
            personAddress = accountSessionPreferences.addressUser

            val prefText = "$personId\n$personEmail\n$personName\n$personPhone\n$personAddress"

            tvName.text = personName
            tvEmail.text = personEmail
        }

        imgProfile.setOnClickListener {
            startActivity(Intent(context, EditProfileActivity::class.java))
        }

        btnKritik.setOnClickListener {
            startActivity(Intent(context, KritikSaranActivity::class.java))
        }

        // Logout Button
        btnLogout.setOnClickListener {

            //set alert dialog builder
            builder = AlertDialog.Builder(context)

            //set title for alert dialog
            builder.setTitle(R.string.dialogTitle)

            //set message for alert dialog
            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(R.drawable.ic_baseline_warning_24)

            //set positive and negative button
            builder.apply {
                setPositiveButton("Yes") { dialogInterface, i ->
                    // Set Account Preferences
                    accountSessionPreferences.logoutAccount()
                    auth.signOut()

                    Tools.showCustomToastSuccess(context, layoutInflater, resources, "Logout Berhasil!")
                    requireActivity().finish()
                    startActivity(Intent(context, SignInActivity::class.java))
                }
                setNegativeButton("No") { dialogInterface, i ->

                }
            }

            // Create the AlertDialog
            alertDialog = builder.create()
            alertDialog.show()

        }

    }
}