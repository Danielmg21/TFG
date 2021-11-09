package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pruebas.utilidades.Utilidades;

public class ShowPills extends AppCompatActivity {

    private ImageButton addPillButton, backButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pills);
        addPillButton =  findViewById(R.id.addPillButton1);
        addPillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPillActivity();
            }
        });
        backButton =  findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backActivity();
            }
        });
    }
    protected void addPillActivity(){
        Intent intent = new Intent(this, AddPillActivity.class);
        startActivity(intent);
    }

    protected void backActivity(){
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }


}