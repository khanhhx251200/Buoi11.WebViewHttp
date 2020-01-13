package com.example.buoi11webviewhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private ImageView refresh;
    private ImageView next;
    private ImageView previous;
    private WebView mWebView;
    private TextView input;
    private ContentLoadingProgressBar mProgressBar;
    private String url = "https://www.24h.com.vn/bong-da/u23-viet-nam-dau-u23-jordan-talkshow-du-doan-ngoi-sao-nao-se-toa-sang-c48a1116152.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initListenner();
        initWebView();

        loadHTTP();
    }

    private void loadHTTP() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://picture.vn/wp-content/uploads/2019/08/Ch%E1%BB%A5p-%E1%BA%A3nh-phong-c%C3%A1ch-h%C3%A0n-qu%E1%BB%91c-4.jpg");
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    Log.d("thinhvh", "run: ");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("thinhvh", "loadHTTP: "+e.getMessage());
                }
            }
        });
        thread.start();
    }

    private void initUI() {
        refresh = findViewById(R.id.button_refresh_url);
        next = findViewById(R.id.button_next);
        previous = findViewById(R.id.button_previous);
        mWebView = findViewById(R.id.webview);
        input = findViewById(R.id.text_url);
        mProgressBar = findViewById(R.id.progress_loading);

        input.setText(url);
    }

    private void initListenner() {
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(input.getText().toString());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.goForward();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.goBack();
            }
        });
    }

    private void initWebView(){
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
                Log.d("thinhvh", "onProgressChanged: " + newProgress);
            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                input.setText(url);

                if (mWebView.canGoBack()) {
                    previous.setImageDrawable(getResources().getDrawable(R.drawable.ic_enable_back));
                } else {
                    previous.setImageDrawable(getResources().getDrawable(R.drawable.ic_disable_back));
                }
                if (mWebView.canGoForward()) {
                    next.setImageDrawable(getResources().getDrawable(R.drawable.ic_enable_forward));
                } else {
                    next.setImageDrawable(getResources().getDrawable(R.drawable.ic_disable_forward));
                }
            }
        });
        mWebView.loadUrl(input.getText().toString());
    }
}
