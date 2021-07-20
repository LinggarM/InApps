package com.incorps.inapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnSignUp: MaterialButton
    private lateinit var tvSignIn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignUp = findViewById(R.id.btn_signup)
        tvSignIn = findViewById(R.id.tv_sign_in)

        btnSignUp.setOnClickListener(this)
        tvSignIn.setOnClickListener(this)
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