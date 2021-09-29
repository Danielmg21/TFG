package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.shape.InterpolateOnScrollPositionChangeHelper;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private TextView txtSpecialDir;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSpecialDir = (TextView) findViewById(R.id.txtSpecialDir);
        /*Ejecuta el codigo del juego 100%
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.loadUrl("file:///android_asset/Project3/index.html");
        */

    }
    public void reaadFromAssets(View view){
        AssetManager am = getAssets();
        try{
            InputStream is = am.open("Project3/js/main.js");
            String result = IOHelper.stringFromStream(is);
            txtSpecialDir.setText(result);
            is.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}