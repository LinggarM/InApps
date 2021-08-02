package com.incorps.inapps

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.utils.Tools

class EditProfileActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var editEmail: TextInputEditText
    private lateinit var editName: TextInputEditText
    private lateinit var editPhone: TextInputEditText
    private lateinit var editAddress: TextInputEditText
    private lateinit var editPassword: TextInputEditText
    private lateinit var btnSimpan: MaterialButton

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private lateinit var progressDialog: ProgressDialog

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        btnBack = findViewById(R.id.img_back_button)
        editEmail = findViewById(R.id.edittext_email)
        editName = findViewById(R.id.edittext_name)
        editPhone = findViewById(R.id.edittext_phone)
        editAddress = findViewById(R.id.edittext_address)
        editPassword = findViewById(R.id.edittext_password)
        btnSimpan = findViewById(R.id.btn_simpan)

        // Set Preferences
        accountSessionPreferences = AccountSessionPreferences(this)

        // Set Progress Dialog
        progressDialog = ProgressDialog(this)

        // Set up Auth
        auth = Firebase.auth

        // Set up Firestore
        db = Firebase.firestore

        // Isi Form
        editEmail.setText(accountSessionPreferences.emailUser)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            editEmail.focusable = View.NOT_FOCUSABLE
        }
        editName.setText(accountSessionPreferences.nameUser)
        editPhone.setText(accountSessionPreferences.phoneUser)
        editAddress.setText(accountSessionPreferences.addressUser)

        btnBack.setOnClickListener {
            finish()
        }

        btnSimpan.setOnClickListener {
            when {
                editName.text.toString() == "" -> {
                    editName.error = resources.getString(R.string.error_name)
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
                editPassword.text.toString().length < 8 -> {
                    editPassword.error = resources.getString(R.string.error_password_length)
                }
                else -> {

                    //set alert dialog builder
                    builder = AlertDialog.Builder(this)

                    //set title for alert dialog
                    builder.setTitle(R.string.dialogTitleEditProfile)

                    //set message for alert dialog
                    builder.setMessage(R.string.dialogMessageEditProfile)
                    builder.setIcon(R.drawable.ic_baseline_warning_24)

                    //set positive and negative button
                    builder.apply {
                        setPositiveButton("Yes") { dialogInterface, i ->
                            // Start Progress Dialog
                            progressDialog.setMessage("Menyimpan Perubahan...")
                            progressDialog.show()

                            // Get Data
                            val name = editName.text.toString().trim()
                            val phone = editPhone.text.toString().trim()
                            val address = editAddress.text.toString().trim()
                            val password = editPassword.text.toString().trim()

                            // Update Auth Name & Password
                            val profileUpdates = userProfileChangeRequest {
                                displayName = name
                            }
                            auth.currentUser!!.updateProfile(profileUpdates)
                                .addOnCompleteListener { task ->
                                    progressDialog.dismiss()
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "User profile updated.")
                                    }
                                }
                            auth.currentUser!!.updatePassword(password)
                                .addOnCompleteListener { task ->
                                    progressDialog.dismiss()
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "User password updated.")
                                    }
                                }

                            // Update Preferences
                            accountSessionPreferences.nameUser = name
                            accountSessionPreferences.phoneUser = phone
                            accountSessionPreferences.addressUser = address

                            // Update Database
                            val userDatabase = hashMapOf(
                                "name" to name,
                                "phone" to phone,
                                "address" to address,
                            )
                            db.collection("user_accounts").document(auth.currentUser!!.uid).set(userDatabase).addOnSuccessListener {
                                progressDialog.dismiss()
                            }
                            finish()
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
    }
}