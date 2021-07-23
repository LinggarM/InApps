package com.incorps.inapps.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.R
import com.incorps.inapps.SignInActivity
import com.incorps.inapps.preferences.AccountSessionPreferences

class ProfileFragment : Fragment() {

    private lateinit var accountSessionPreferences: AccountSessionPreferences

    private lateinit var tv1: TextView
    private lateinit var tv2: TextView
    private lateinit var tv3: TextView
    private lateinit var btnLogout: Button

    private lateinit var builder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog

    private lateinit var auth: FirebaseAuth

    private var personId: String = ""
    private var personEmail: String = ""
    private var personName: String = ""
    private var personPhoto: Uri? = null
    private var personePhone: String = ""
    private var personAddress: String = ""
    private var personPassword: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv1 = view.findViewById(R.id.tv_1)
        tv2 = view.findViewById(R.id.tv_2)
        tv3 = view.findViewById(R.id.tv_3)

        accountSessionPreferences = AccountSessionPreferences(requireContext())

        // Logout Button
        btnLogout = view.findViewById(R.id.btn_logout)
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

                    startActivity(Intent(context, SignInActivity::class.java))
                    requireActivity().finish()
                }
                setNegativeButton("No") { dialogInterface, i ->

                }
            }

            // Create the AlertDialog
            alertDialog = builder.create()
            alertDialog.show()

        }

        // Data from Google Sign In
        val acct = GoogleSignIn.getLastSignedInAccount(context)
        if (acct != null) {
            personId = acct.id.toString()
            personEmail = acct.email.toString()
            personName = acct.displayName.toString()
            personPhoto = acct.photoUrl
            val acctText = "$personId, $personEmail, $personName, $personPhoto"
            tv1.text = acctText
        }

        // Data from Firebase
        auth = Firebase.auth
        val user = auth.currentUser
        if (user != null) {
            personId = user.uid
            personEmail = user.email.toString()
            personName = user.displayName.toString()
            personePhone = user.phoneNumber.toString()
            personPhoto = user.photoUrl
            val userText = "$personId, $personEmail, $personName, $personePhone, $personPhoto"
            tv2.text = userText
        }

        // Data from Shared Preferences
        if (accountSessionPreferences.isLogin) {
            personId = accountSessionPreferences.idUser
            personEmail = accountSessionPreferences.emailUser
            personName = accountSessionPreferences.nameUser
            personePhone = accountSessionPreferences.phoneUser
            personAddress = accountSessionPreferences.addressUser
            personPassword = accountSessionPreferences.passwordUser

            val prefText = "$personId, $personEmail, $personName, $personePhone, $personAddress, $personPassword"
            tv3.text = prefText
        }

    }
}