package com.incorps.inapps.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.R
import com.incorps.inapps.SignInActivity

class ProfileFragment : Fragment() {

    private lateinit var btnLogout: Button
    private lateinit var auth: FirebaseAuth

    private lateinit var builder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

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
    }
}