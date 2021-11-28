package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {

    private Button buttonGame;
    private ImageButton showPillButton, ajustes;


    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        crearCanal();

        buttonGame = (Button) findViewById(R.id.playButton);
        buttonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
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
            public void onClick(View v) { crearNotificacion();}
        });
    }

    private void crearNotificacion() {
        Toast.makeText(MainScreen.this, "reminder", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainScreen.this, Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainScreen.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeClick = System.currentTimeMillis();
        long tenSec = 1000 * 5;
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                timeClick + tenSec,
                pendingIntent);
    }

    protected void pruebasActivity() {
        Intent intent = new Intent(this, ModifyMedicine.class);
        startActivity(intent);
    }

    protected void openGameActivity(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    protected void openShowPillActivity(){
        Intent intent = new Intent(this, ShowPills.class);
        startActivity(intent);
    }

    private void crearCanal(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Toast.makeText(MainScreen.this, "Creaando canal", Toast.LENGTH_SHORT).show();
            CharSequence name = "CANAL";
            String descripcion = "DEscripcion del canal";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel canal = new NotificationChannel("notificationID", name, importancia);
            canal.setDescription(descripcion);

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

}


