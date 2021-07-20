package com.incorps.inapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnLogin: MaterialButton
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnLogin = findViewById(R.id.btn_login)
        tvForgotPassword = findViewById(R.id.tv_forgot_password)
        tvSignUp = findViewById(R.id.tv_sign_up)

        btnLogin.setOnClickListener(this)
        tvForgotPassword.setOnClickListener(this)
        tvSignUp.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            btnLogin ->  {
                val intentDashboardActivity = Intent(this, DashboardActivity::class.java)
                startActivity(intentDashboardActivity)
            }
            tvForgotPassword ->  {
                val intentForgotPasswordActivity = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intentForgotPasswordActivity)
            }
            tvSignUp ->  {
                val intentSignUpActivity = Intent(this, SignUpActivity::class.java)
                startActivity(intentSignUpActivity)
            }
        }
    }
}