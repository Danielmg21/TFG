package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pruebas.utilidades.Utilidades;

public class ModifyMedicine extends AppCompatActivity {

    DataBaseHelper conn;
    private TextView nombre, numeroPastillas;
    private Button consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_medicine);
        conn = new DataBaseHelper(getApplicationContext(),"bd_medicine", null, 1);
        nombre = findViewById(R.id.campoMedicine);
        numeroPastillas = findViewById(R.id.campoCantidad);
        consulta = findViewById(R.id.consultar);
        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { consultar();}
        });
    }

    private void consultar(){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {nombre.getText().toString()};
        String[] campos = {Utilidades.CAMPO_NAME, Utilidades.CAMPO_CANTIDAD};
        try{
            Cursor cursor = db.query(Utilidades.TABLE_MEDICINE, campos, Utilidades.CAMPO_NAME+"=?", parametros, null, null, null);
            cursor.moveToFirst();
            numeroPastillas.setText(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe", Toast.LENGTH_LONG).show();
            limpiar();
        }


    }

    private void limpiar() {
        nombre.setText("");
        numeroPastillas.setText("");
    }
}