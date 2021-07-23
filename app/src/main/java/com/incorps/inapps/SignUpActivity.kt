package com.incorps.inapps

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.utils.Tools

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var editName: TextInputEditText
    private lateinit var editEmail: TextInputEditText
    private lateinit var editPhone: TextInputEditText
    private lateinit var editAddress: TextInputEditText
    private lateinit var editPassword: TextInputEditText
    private lateinit var checkTermsPrivacy: CheckBox
    private lateinit var btnSignUp: MaterialButton
    private lateinit var tvSignIn: TextView

    private lateinit var progressDialog: ProgressDialog

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        editName = findViewById(R.id.edittext_name)
        editEmail = findViewById(R.id.edittext_email)
        editPhone = findViewById(R.id.edittext_phone)
        editAddress = findViewById(R.id.edittext_address)
        editPassword = findViewById(R.id.edittext_password)
        checkTermsPrivacy = findViewById(R.id.checkbox_terms_privacy)
        btnSignUp = findViewById(R.id.btn_signup)
        tvSignIn = findViewById(R.id.tv_sign_in)

        btnSignUp.setOnClickListener(this)
        tvSignIn.setOnClickListener(this)

        // Set Progress Dialog
        progressDialog = ProgressDialog(this)

        // Set up Auth
        auth = Firebase.auth

        // Set up Firestore
        db = Firebase.firestore
    }

    override fun onClick(view: View?) {
        when (view) {
            btnSignUp -> {
                when {
                    editName.text.toString() == "" -> {
                        editName.error = resources.getString(R.string.error_name)
                    }
                    editEmail.text.toString() == "" -> {
                        editEmail.error = resources.getString(R.string.error_email)
                    }
                    editPhone.text.toString() == "" -> {
                        editPhone.error = resources.getString(R.string.error_phone)
                    }
                    editAddress.text.toString() == "" -> {
                        editAddress.error = resources.getString(R.string.error_address)
                    }
                    editPassword.text.toString() == "" -> {
                        editPassword.error = resources.getString(R.string.error_password)
                    }
                    !checkTermsPrivacy.isChecked -> {
                        checkTermsPrivacy.error = resources.getString(R.string.error_terms)
                        Tools.showCustomToastInfo(this, layoutInflater, resources, resources.getString(R.string.error_terms))
                    }
                    else -> {
                        signUp(
                            editName.text.toString().trim(),
                            editEmail.text.toString().trim(),
                            editPhone.text.toString().trim(),
                            editAddress.text.toString().trim(),
                            editPassword.text.toString().trim())
                    }
                }
            }
            tvSignIn -> {
                val intentSignInActivity = Intent(this, SignInActivity::class.java)
                startActivity(intentSignInActivity)
            }
        }
    }

    private fun signUp(name: String, email: String, phone: String, address: String, password: String) {

        // Start Progress Dialog
        progressDialog.setMessage("Registering...")
        progressDialog.show()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->

                            if (task.isSuccessful) {
                                progressDialog.dismiss()

                                // Change Display Name
                                val user = auth.currentUser
                                user?.let {
                                    val changeDisplayName = userProfileChangeRequest {
                                        displayName = name
                                    }
                                    it.updateProfile(changeDisplayName)
                                    addToDatabase(it, phone, address, password)
                                }

                            } else {
                                progressDialog.dismiss()

                                Tools.showCustomToastFailed(this, layoutInflater, resources, "Registrasi akun gagal! Ulangi dengan email berbeda")
                            }
                        }
                } else {
                    progressDialog.dismiss()

                    // If sign in fails, display a message to the user.
                    Tools.showCustomToastFailed(this, layoutInflater, resources, "Registrasi akun gagal! Ulangi dengan email berbeda")
                }
            }
    }

    private fun addToDatabase(user: FirebaseUser, phone: String, address: String, password: String) {

        val userDatabase = hashMapOf(
            "name" to user.displayName.toString(),
            "email" to user.email.toString(),
            "phone" to phone,
            "address" to address,
            "password" to password
        )

        db.collection("user_accounts")
            .document(user.uid)
            .set(userDatabase)
            .addOnSuccessListener {

                // Show Toast
                val message = "Registrasi akun berhasil! Silahkan login dengan akun yang sudah terdaftar"
                Tools.showCustomToastSuccess(this, layoutInflater, resources, message)

                auth.signOut()
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
            .addOnFailureListener {

                // Show Toast
                val message = "Registrasi akun gagal!"
                Tools.showCustomToastFailed(this, layoutInflater, resources, message)

                auth.signOut()
            }
    }
}