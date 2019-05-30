package com.incorps.inapps.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.incorps.inapps.R;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        Toolbar toolbarTerms = findViewById(R.id.toolbar_terms);
        toolbarTerms.setTitle("Terms of Service");
        setSupportActionBar(toolbarTerms);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView webViewTerms = findViewById(R.id.wv_terms);
        WebSettings webSettingsTerms = webViewTerms.getSettings();
        webViewTerms.setWebViewClient(new WebViewClient());
        webViewTerms.loadUrl("https://www.in-corps.com/terms");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
