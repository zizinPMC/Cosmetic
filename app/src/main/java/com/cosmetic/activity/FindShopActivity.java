package com.cosmetic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cosmetic.R;

/**
 * Created by yujeen on 2017. 11. 7..
 */

public class FindShopActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findshop);

        Intent intent = getIntent();
        webView = (WebView)findViewById(R.id.map_webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(intent.getExtras().getString("url"));
        webView.setWebViewClient(new WebViewClientClass());
    }
    private class WebViewClientClass extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
