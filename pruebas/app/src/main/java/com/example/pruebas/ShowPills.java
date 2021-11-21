package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pruebas.entidades.Medicine;
import com.example.pruebas.utilidades.Utilidades;

import java.util.ArrayList;

public class ShowPills extends AppCompatActivity {

    private ImageButton addPillButton, backButton;
    private ListView listViewPill;
    ArrayList<String> listaInformacion;
    ArrayList<Medicine> listaPill;
    public DataBaseHelper conn;

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
        listViewPill = findViewById(R.id.listViewPill);
        conn = new DataBaseHelper(getApplicationContext(),"bd_medicine", null, 1);
        consultarListaMedicine();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listViewPill.setAdapter(adaptador);
        listViewPill.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l){
                String nombre = listaPill.get(pos).getName();
                int cantidad = listaPill.get(pos).getCantidad();
                int hora = listaPill.get(pos).getHora();
                int minutos = listaPill.get(pos).getMinutos();
                ArrayList<String> info = new ArrayList();
                info.add(nombre);
                info.add(String.valueOf(cantidad));
                info.add(String.valueOf(hora));
                info.add(String.valueOf(minutos));
                Intent intent = new Intent(ShowPills.this,ModifyMedicine.class);
                //intent.putExtra("key", info);
                startActivity(intent);
            }
        });
    }

    private void consultarListaMedicine() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Medicine medicine;
        listaPill = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLE_MEDICINE, null);

        while(cursor.moveToNext()){
            medicine = new Medicine();
            medicine.setName(cursor.getString(0));
            medicine.setCantidad(cursor.getInt(1));
            medicine.setHora(cursor.getInt(2));
            medicine.setMinutos(cursor.getInt(3));
            listaPill.add(medicine);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();
        for(int i = 0; i < listaPill.size(); i++){
            //Por decision de diseño no mostrar la cantidad de pastillas en la ventana de mostrar AllPills
            //listaInformacion.add(listaPill.get(i).getName() +" --- Cantidad --- " + listaPill.get(i).getCantidad() +" --- Horario --- " + listaPill.get(i).getHora() + ":" + listaPill.get(i).getMinutos());
            listaInformacion.add(listaPill.get(i).getName() +" | --- | Horario | --- | " + listaPill.get(i).getHora() + ":" + listaPill.get(i).getMinutos());
        }
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