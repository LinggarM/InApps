package com.incorps.inapps

import android.app.ProgressDialog
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
                    // Start Progress Dialog
                    progressDialog.setMessage("Registering...")
                    progressDialog.show()

                    val emailAddress = editEmail.text.toString()
                    auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                progressDialog.dismiss()
                                Tools.showCustomToastSuccess(this, layoutInflater, resources, "Link reset sandi berhasil dikirim!")
                                finish()
                            } else {
                                progressDialog.dismiss()
                                Tools.showCustomToastFailed(this, layoutInflater, resources, "Reset sandi gagal!")
                            }
                        }
                }
            }
        }
    }
}