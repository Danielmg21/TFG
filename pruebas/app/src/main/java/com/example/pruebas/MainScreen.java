package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainScreen extends AppCompatActivity {

    private Button buttonGame;
    private ImageButton showPillButton, ajustes;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

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
            public void onClick(View v) { }
        });
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
