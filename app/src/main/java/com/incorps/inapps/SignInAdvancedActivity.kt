package com.incorps.inapps

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.utils.Tools

class SignInAdvancedActivity : AppCompatActivity() {

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private lateinit var progressDialog: ProgressDialog

    private lateinit var editPhone: TextInputEditText
    private lateinit var editAddress: TextInputEditText
    private lateinit var btnProceed: MaterialButton

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_advanced)

        editPhone = findViewById(R.id.edittext_phone)
        editAddress = findViewById(R.id.edittext_address)
        btnProceed = findViewById(R.id.btn_proceed)

        accountSessionPreferences = AccountSessionPreferences(this)

        // Set Progress Dialog
        progressDialog = ProgressDialog(this)

        // Set up Auth
        auth = Firebase.auth

        // Set up Firestore
        db = Firebase.firestore

        btnProceed.setOnClickListener {
            when {
                editPhone.text.toString() == "" -> {
                    editPhone.error = resources.getString(R.string.error_phone)
                }
                editAddress.text.toString() == "" -> {
                    editAddress.error = resources.getString(R.string.error_address)
                }
                else -> {
                    signIn(editPhone.text.toString(), editAddress.text.toString())
                }
            }
        }
    }

    private fun signIn(phone: String, address: String) {
        progressDialog.setMessage(resources.getString(R.string.loggingin))
        progressDialog.show()

        // Get data from Intent
        val id = intent.getStringExtra("IdUser").toString()
        val email = intent.getStringExtra("EmailUser").toString()
        val name = intent.getStringExtra("NameUser").toString()

        val userDatabase = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "address" to address,
        )

        db.collection("user_accounts")
            .document(id)
            .set(userDatabase)
            .addOnCompleteListener {
                progressDialog.dismiss()

                accountSessionPreferences.isLogin = true
                accountSessionPreferences.idUser = id
                accountSessionPreferences.emailUser = email
                accountSessionPreferences.nameUser = name
                accountSessionPreferences.phoneUser = phone
                accountSessionPreferences.addressUser = address

                // Show Toast
                val message = "Login berhasil! Selamat datang di In-Apps"
                Tools.showCustomToastSuccess(this, layoutInflater, resources, message)
                finish()

                startActivity(Intent(this, DashboardActivity::class.java))
            }
            .addOnFailureListener {
                auth.signOut()
                progressDialog.dismiss()

                // Show Toast
                val message = "Login gagal! Coba Lagi"
                Tools.showCustomToastFailed(this, layoutInflater, resources, message)
                finish()

                startActivity(Intent(this, SignInActivity::class.java))
            }
    }

    override fun onBackPressed() {
        auth.signOut()
        finish()

        startActivity(Intent(this, SignInActivity::class.java))
    }
}