package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.pruebas.utilidades.Utilidades;

import java.util.Locale;

public class AddPillActivity extends AppCompatActivity {

    Button timeButton;
    int hour, minute;
    ImageButton cancel, save;
    EditText nombre,cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);
        nombre = findViewById(R.id.cantidad1);
        cantidad = findViewById(R.id.cantidad);
        timeButton = findViewById(R.id.chooseDate);

        //Método para cancelar la creacion de la pastilla
        cancel = findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddPillActivity.this);
                builder.setMessage("No se guardarán los cambios realizados, ¿Seguro que quieres continuar?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openShowPillActivity();
                    }
                }).setNegativeButton("No",null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        //Método para guardar la informacion en la base de datos
        save = findViewById(R.id.addPillButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AddPillActivity.this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
                registrarMedicine();
                openShowPillActivity();
            }
        });
    }

    private void openShowPillActivity() {
        Intent intent = new Intent(this, ShowPills.class);
        startActivity(intent);
    }

    public void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {
                hour = selectHour;
                minute = selectMinute;
                timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d", hour, minute));
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

    private void registrarMedicine(){
        DataBaseHelper helper = new DataBaseHelper(AddPillActivity.this, "bd_medicine",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NAME,nombre.getText().toString());
        values.put(Utilidades.CAMPO_CANTIDAD,cantidad.getText().toString());
        values.put(Utilidades.CAMPO_HORA, hour);
        values.put(Utilidades.CAMPO_MINUTOS, minute);
        Long idResultado = db.insert(Utilidades.TABLE_MEDICINE,Utilidades.CAMPO_NAME,values);
        Toast.makeText(getApplicationContext(),"Id registro"+idResultado,Toast.LENGTH_SHORT).show();
        db.close();
    }

}