package com.incorps.inapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var editEmail: TextInputEditText
    private lateinit var editName: TextInputEditText
    private lateinit var editPhone: TextInputEditText
    private lateinit var checkTermsPrivacy: CheckBox
    private lateinit var editPassword: TextInputEditText
    private lateinit var btnSignUp: MaterialButton
    private lateinit var tvSignIn: TextView

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        editEmail = findViewById(R.id.edittext_email)
        editName = findViewById(R.id.edittext_name)
        editPhone = findViewById(R.id.edittext_phone)
        editPassword = findViewById(R.id.edittext_password)
        checkTermsPrivacy = findViewById(R.id.checkbox_terms_privacy)
        btnSignUp = findViewById(R.id.btn_signup)
        tvSignIn = findViewById(R.id.tv_sign_in)

        btnSignUp.setOnClickListener(this)
        tvSignIn.setOnClickListener(this)

        // Set up Firestore
        db = Firebase.firestore
    }

    override fun onClick(view: View?) {
        when(view) {
            btnSignUp -> {
                val intentSignInActivity = Intent(this, SignInActivity::class.java)
                startActivity(intentSignInActivity)
            }
            tvSignIn -> {
                val intentSignInActivity = Intent(this, SignInActivity::class.java)
                startActivity(intentSignInActivity)
            }
        }
    }
}