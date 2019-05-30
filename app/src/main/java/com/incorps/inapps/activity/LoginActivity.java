package com.incorps.inapps.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.incorps.inapps.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout txtEmail;
    private TextInputLayout txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.etxt_email);
        txtPassword = findViewById(R.id.etxt_password);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intentBtnLogin;
                intentBtnLogin = new Intent(view.getContext(), DashboardActivity.class);
                startActivity(intentBtnLogin);
            }
        });

        TextView dontHaveAccount = findViewById(R.id.txt_dont_have_account);
        String textDontHaveAccount = "Belum punya akun? Daftar";
        SpannableString ssDontHaveAccount = new SpannableString(textDontHaveAccount);

        ClickableSpan clickDontHaveAccount = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intentDontHaveAccount;
                intentDontHaveAccount = new Intent(widget.getContext(), RegisterActivity.class);
                startActivity(intentDontHaveAccount);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
                ds.setUnderlineText(true);
            }
        };

        ssDontHaveAccount.setSpan(clickDontHaveAccount, 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        dontHaveAccount.setText(ssDontHaveAccount);
        dontHaveAccount.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private boolean validateEmail(){
        String emailInput = txtEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            txtEmail.setError("Field can't be empty");
            return false;
        } else {
            txtEmail.setError(null);
            return true;
        }
    }

    private boolean valitadePassword(){
        String passwordInput = txtPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            txtPassword.setError("Field can't be empty");
            return false;
        } else {
            txtPassword.setError(null);
            return true;
        }
    }

    public void confirmInput(View view) {
        if (!validateEmail() | !valitadePassword()){
            return;
        }
    }
}
