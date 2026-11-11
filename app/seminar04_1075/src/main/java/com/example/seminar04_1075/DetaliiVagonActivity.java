package com.example.seminar04_1075;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class DetaliiVagonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_vagon);

        String link = getIntent().getStringExtra("link");

        WebView webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());

        if (link != null) {
            webView.loadUrl(link);
        }
    }
}
