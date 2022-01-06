package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;



import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pruebas.utilidades.MediaManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;


public class MainScreen extends AppCompatActivity{

    private Button buttonGame, pruebas;
    private ImageButton showPillButton, ajustes, musicOn, musicOff;
    private String channelID = "notifyChanel";
    private MediaManager manager;
    public int music;


    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        crearCanal();
        manager = new MediaManager(this);
        manager.startMusic();

        buttonGame =  findViewById(R.id.playButton);
        buttonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manager.getMusic() != null){
                    manager.stopMuscic();
                }
                openGameActivity();
            }
        });
        pruebas =  findViewById(R.id.pruebas);
        pruebas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        showPillButton = findViewById(R.id.timeButton);
        showPillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShowPillActivity();
            }
        });
        ajustes = findViewById(R.id.ajustes);
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        musicOn = findViewById(R.id.musicOnButton);
        musicOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                manager.startMusic();
            }
        });
        musicOff = findViewById(R.id.musicOffButton);
        musicOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manager.getMusic() != null){
                    manager.stopMuscic();
                }
            }
        });
    }

    protected void openGameActivity(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    protected void openShowPillActivity(){
        Intent intent = new Intent(this, ShowPills.class);
        if(manager.getMusic() != null){
            music = 1;
        }else{
            music = 0;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("music", music);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void crearCanal(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "CANAL";
            String descripcion = "Descripcion del canal";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel canal = new NotificationChannel("notifyChanel", name, importancia);
            canal.setDescription(descripcion);
            canal.enableLights(true);
            canal.enableVibration(true);
            canal.setLightColor(Color.RED);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(canal);
        }
    }

    //Método para preguntar si queremos salir de la APP cuando pulsan el boton de back
    @Override
    public void onBackPressed(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainScreen.this);
        builder.setMessage("¿Quieres salir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainScreen.super.onBackPressed();
                    }
                }).setNegativeButton("No",null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(manager.getMusic() != null){
            manager.setPaused(manager.time());
            manager.pauseMusic();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(manager.getMusic() != null) {
            manager.startMusic();
        }
    }

}


