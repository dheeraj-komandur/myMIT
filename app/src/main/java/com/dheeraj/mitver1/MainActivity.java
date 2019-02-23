package com.dheeraj.mitver1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class MainActivity extends AppCompatActivity {
WebView webView;
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        webView = findViewById(R.id.webview);
//        webView.setInitialScale(1);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        webView.setScrollbarFadingEnabled(false);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setDisplayZoomControls(false);
//        String url ="https://erp.mitwpu.edu.in/AdminLogin.aspx";
//        webView.loadUrl(url);
//        webView.setWebViewClient(new MyWebViewClient());

    }


        private class MyWebViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

               // Connection.Response res = Jsoup.connect(url);
                //String mycookies = new String();

               // Toast.makeText(MainActivity.this, myCookies, Toast.LENGTH_SHORT).show();
                //String session = new String();

                String myCookies = CookieManager.getInstance().getCookie(url);
                String session = myCookies.substring(myCookies.lastIndexOf("=") + 1);


                String str = new String(url);
                String check = new String("https://erp.mitwpu.edu.in/AdminLogin.aspx");

                if (!str.equals(check))
                {
                    DbService dbService = new DbService(MainActivity.this);
                    dbService.StoreSession(session);
                    Intent intent = new Intent(MainActivity.this,AttandanceListView.class);
                    startActivity(intent);
                }
                else
                    {
                        view.loadUrl(url);
                    }
                return true;
            }
        }
}
