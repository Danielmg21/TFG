package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pruebas.entidades.Medicine;
import com.example.pruebas.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Locale;

public class ModifyMedicine extends AppCompatActivity {

    DataBaseHelper conn;
    private TextView campoNombre, campoCantidad;
    private Button  eliminar, modificar, timeButton;
    private ImageButton back;
    private String nombreMedicine;
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_medicine);
        campoNombre = findViewById(R.id.campoMedicine);
        campoCantidad = findViewById(R.id.campoCantidad);
        timeButton = findViewById(R.id.chooseDate);
        conn = new DataBaseHelper(getApplicationContext(),"bd_medicine", null, 1);
        Bundle extras = getIntent().getExtras();
        Medicine medicine = null;
        if(extras != null){
            medicine = (Medicine) extras.getSerializable("medicina");
            campoNombre.setText(medicine.getName());
            campoCantidad.setText(String.valueOf(medicine.getCantidad()));
            timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d", medicine.getHora(), medicine.getMinutos()));
            nombreMedicine = medicine.getName();
        }

        eliminar = findViewById(R.id.eliminar);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarDatos();
                volver();
            }
        });
        modificar = findViewById(R.id.modificar);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarDatos(nombreMedicine);
                volver();
            }
        });

        back = findViewById(R.id.modifyBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {volver();}
        });
    }

    private void volver() {
        Intent intent = new Intent(this, ShowPills.class);
        startActivity(intent);
    }


    //Metdo encargdo de eliminar la infomacion guardada en la base de datos
    //buscando por el nombre del medicamento
    private void eliminarDatos() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {campoNombre.getText().toString()};

        db.delete(Utilidades.TABLE_MEDICINE, Utilidades.CAMPO_NAME+"=?", parametros);
        Toast.makeText(getApplicationContext(),"Dato eliminado", Toast.LENGTH_LONG).show();
        limpiar();
        db.close();
    }

    //Modificar la informacion de un medicamento buscando por su nombre
    private void modificarDatos(String nombreMedicine){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {nombreMedicine};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NAME, campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_CANTIDAD, campoCantidad.getText().toString());
        values.put(Utilidades.CAMPO_HORA, hour);
        values.put(Utilidades.CAMPO_MINUTOS, minute);
        db.update(Utilidades.TABLE_MEDICINE, values, Utilidades.CAMPO_NAME+"=?", parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizo", Toast.LENGTH_LONG).show();
        limpiar();
        db.close();
    }
    //Metodo que se encarga de limpiar los campos del registro
    private void limpiar() {
        campoNombre.setText("");
        campoCantidad.setText("");
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

}