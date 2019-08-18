package com.sample.parserlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setLoadWithOverviewMode(true);
        //webview.getSettings().setUseWideViewPort(true);

        webview.getSettings().setDisplayZoomControls(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);

        setContentView(webview);
        String url = getIntent().getStringExtra("LOAD_URL");
        String data = getIntent().getStringExtra("LOAD_DATA");
        if (data != null) {
            webview.loadData(data, "text/html", "utf-8");
        } else if (url != null) {
            webview.loadUrl(url);
        }
    }

    public static Intent getCallIntent(Context context) {
        return new Intent(context, WebViewActivity.class);
    }
}
