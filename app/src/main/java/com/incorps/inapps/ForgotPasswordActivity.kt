package com.incorps.inapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var imgBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        imgBack = findViewById(R.id.img_back_button)
        imgBack.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            imgBack -> {
                finish()
            }
        }
    }
}