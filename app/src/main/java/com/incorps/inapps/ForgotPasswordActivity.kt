package com.incorps.inapps

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.utils.Tools

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    private lateinit var imgBack: ImageView
    private lateinit var editEmail: TextInputEditText
    private lateinit var btnReset: MaterialButton

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        imgBack = findViewById(R.id.img_back_button)
        editEmail = findViewById(R.id.edit_email)
        btnReset = findViewById(R.id.btn_reset)

        // Set up Auth
        auth = Firebase.auth

        imgBack.setOnClickListener(this)
        btnReset.setOnClickListener(this)

        // Set Progress Dialog
        progressDialog = ProgressDialog(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            imgBack -> {
                finish()
            }
            btnReset -> {
                if (editEmail.text.toString() == "") {
                    editEmail.error = resources.getString(R.string.error_email)
                } else {
                    //set alert dialog builder
                    builder = AlertDialog.Builder(this)

                    //set title for alert dialog
                    builder.setTitle(R.string.dialogTitleForgotPassword)

                    //set message for alert dialog
                    builder.setMessage(R.string.dialogMessageForgotPassword)
                    builder.setIcon(R.drawable.ic_baseline_warning_24)

                    //set positive and negative button
                    builder.apply {
                        setPositiveButton("Yes") { dialogInterface, i ->
                            // Start Progress Dialog
                            progressDialog.setMessage("Registering...")
                            progressDialog.show()

                            val emailAddress = editEmail.text.toString()
                            auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        progressDialog.dismiss()
                                        Tools.showCustomToastSuccess(this@ForgotPasswordActivity, layoutInflater, resources, "Link reset sandi berhasil dikirim!")
                                        finish()
                                    } else {
                                        progressDialog.dismiss()
                                        Tools.showCustomToastFailed(this@ForgotPasswordActivity, layoutInflater, resources, "Reset sandi gagal!")
                                    }
                                }
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