package com.incorps.inapps.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.incorps.inapps.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView youAgree = findViewById(R.id.txt_youagree);
        String textYouAgree = "Dengan mendaftar, anda menyetujui Syarat dan Ketentuan yang berlaku di In Corporation";
        SpannableString ssYouAgree = new SpannableString(textYouAgree);

        TextView haveAccount = findViewById(R.id.txt_haveaccount);
        String textHaveAccount = "Sudah punya akun? Login";
        SpannableString ssHaveAccount = new SpannableString(textHaveAccount);

        ClickableSpan clickYouAgree = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intentYouAgree;
                intentYouAgree = new Intent(widget.getContext(), TermsActivity.class);
                startActivity(intentYouAgree);
            }
        };

        ClickableSpan clickHaveAccount = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intentHaveAccount;
                intentHaveAccount = new Intent(widget.getContext(), LoginActivity.class);
                startActivity(intentHaveAccount);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
                ds.setUnderlineText(true);
            }
        };

        ssYouAgree.setSpan(clickYouAgree, 34, 54, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        youAgree.setText(ssYouAgree);
        youAgree.setMovementMethod(LinkMovementMethod.getInstance());

        ssHaveAccount.setSpan(clickHaveAccount, 18, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        haveAccount.setText(ssHaveAccount);
        haveAccount.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
