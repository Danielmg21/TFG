package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pruebas.utilidades.Utilidades;

import java.util.ArrayList;

public class ModifyMedicine extends AppCompatActivity {

    DataBaseHelper conn;
    private TextView campoNombre, campoCantidad;
    private Button consulta, eliminar, modificar, limpiar;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_medicine);
        conn = new DataBaseHelper(getApplicationContext(),"bd_medicine", null, 1);
        //Bundle extras = getIntent().getExtras();
        /*ArrayList<String> data = extras.getStringArrayList("key");
        String nombre = data.get(0);
        int cantidad = Integer.parseInt(data.get(1));
        String hora = data.get(2);
        campoNombre.setText(nombre);
        campoCantidad.setText(cantidad);
        */
        campoNombre = findViewById(R.id.campoMedicine);
        campoCantidad = findViewById(R.id.campoCantidad);
        consulta = findViewById(R.id.consultar);
        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { consultarDatos();}
        });

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
                modificarDatos();
                volver();
            }
        });
        limpiar = findViewById(R.id.limpiar);
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wipeBBDD();
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

    //Metodo para consultar informacion sobre un dato por medio del nombre
    private void consultarDatos(){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {campoNombre.getText().toString()};
        String[] campos = {Utilidades.CAMPO_NAME, Utilidades.CAMPO_CANTIDAD};
        try{
            Cursor cursor = db.query(Utilidades.TABLE_MEDICINE, campos, Utilidades.CAMPO_NAME+"=?", parametros, null, null, null);
            cursor.moveToFirst();
            campoCantidad.setText(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe", Toast.LENGTH_LONG).show();
            limpiar();
        }
    }

    //Modificar la informacion de un medicamento buscando por su nombre
    private void modificarDatos(){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {campoNombre.getText().toString()};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NAME, campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_CANTIDAD, campoCantidad.getText().toString());
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

    private void wipeBBDD(){
        SQLiteDatabase db = conn.getReadableDatabase();
        db.execSQL("DELETE FROM medicine");
        db.close();
    }
}