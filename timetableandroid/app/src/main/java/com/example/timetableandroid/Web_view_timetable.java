//package com.example.timetableandroid;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.webkit.WebResourceError;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//public class Web_view_timetable extends AppCompatActivity {
//
//    private WebView webView;
//    SharedPreferences sh;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_web_view_timetable);
//
//        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//        // Find the WebView in the layout
//        webView = findViewById(R.id.webView);
//
//        // Configure WebView settings
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
//
//        // Load a web page
////        webView.loadUrl("https://www.example.com");
//        webView.loadUrl(sh.getString("ip", "")+"/api_androidviewtimetable");
//
////        http://192.168.29.180:5502/hodviewtimetablepost
//
//        // Set a WebViewClient to handle events
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                // Page started loading
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                // Page finished loading
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                // Handle error
//            }
//        });
//    }
//
//    // Handle back button press for WebView navigation
//    @Override
//    public void onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            super.onBackPressed();
//        }
//    }
//}


package com.example.timetableandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web_view_timetable extends AppCompatActivity {

    private WebView webView;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_timetable);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Find the WebView in the layout
        webView = findViewById(R.id.webView);

        // Configure WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
        webSettings.setBuiltInZoomControls(true); // Enable zoom controls
        webSettings.setDisplayZoomControls(true); // Display zoom controls

        // Load a web page
        webView.loadUrl(sh.getString("ip", "") + "/api_androidviewtimetable");

        // Set a WebViewClient to handle events
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Page started loading
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Page finished loading
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // Handle error
            }
        });
    }

    // Handle back button press for WebView navigation
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
