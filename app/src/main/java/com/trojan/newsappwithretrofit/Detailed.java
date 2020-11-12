package com.trojan.newsappwithretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detailed extends AppCompatActivity {

    TextView tvTitle,tvSource,tvDate,tvDescrpition;
    WebView webView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvSource= findViewById(R.id.tvSource);
        tvDescrpition = findViewById(R.id.tvDetailed);
        webView = findViewById(R.id.webView);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String date = intent.getStringExtra("date");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");
        String desc = intent.getStringExtra("desc");

        tvDescrpition.setText(desc);
        tvSource.setText(source);
        tvTitle.setText(title);
        tvDate.setText(date);

        Picasso.with(Detailed.this).load(imageUrl).into(imageView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}