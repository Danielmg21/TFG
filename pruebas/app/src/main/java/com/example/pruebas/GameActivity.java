package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    private WebView webview;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.loadUrl("file:///android_asset/Project3/index.html");
        //(new Handler()).postDelayed(this::esperar, 2500);
    }

    public void esperar(){
        Instrumentation inst = new Instrumentation();
        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_SPACE);
    }

    @Override
    public void onBackPressed(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(GameActivity.this);
        builder.setMessage("Acuerdaté de guardar la partida antes de salir. ¿Quieres salir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameActivity.super.onBackPressed();
                    }
                }).setNegativeButton("No",null);
        AlertDialog alert = builder.create();
        alert.show();
    }

}