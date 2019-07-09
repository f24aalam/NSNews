package com.w3students.nsnews;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    String url;
    String Title;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alertDialog = new SpotsDialog
                .Builder()
                .setContext(this)
                .setMessage("Loading Article...")
                .setCancelable(false)
                .build();

        alertDialog.show();

        Intent i = getIntent();
        url = i.getStringExtra("url");
        Title = i.getStringExtra("title");
        webView = findViewById(R.id.fullcontent);
        webView.loadUrl(url);
        webView.setWebViewClient(new MyBrowser());

        this.setTitle(Title);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            alertDialog.dismiss();
        }
    }

}
