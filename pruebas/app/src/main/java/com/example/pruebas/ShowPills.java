package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.pruebas.entidades.Medicine;
import com.example.pruebas.utilidades.MediaManager;
import com.example.pruebas.utilidades.Utilidades;

import java.util.ArrayList;

public class ShowPills extends AppCompatActivity {

    private ImageButton addPillButton, backButton;
    private ListView listViewPill;
    ArrayList<String> listaInformacion;
    ArrayList<Medicine> listaPill;
    public DataBaseHelper conn;
    public MediaManager manager;
    public int music;
    public String hora, minutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pills);
        Bundle extras = getIntent().getExtras();
        music = extras.getInt("music");
        if (music == 1 ){
            manager = new MediaManager(this);
            manager.startMusicShowPill();
        }
        addPillButton = findViewById(R.id.addPillButton1);
        addPillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPillActivity();
            }
        });
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backActivity();
            }
        });
        listViewPill = findViewById(R.id.listViewPill);
        conn = new DataBaseHelper(getApplicationContext(), "bd_medicine", null, 1);
        consultarListaMedicine();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listViewPill.setAdapter(adaptador);
        listViewPill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Medicine medicine = listaPill.get(pos);
                Intent intent = new Intent(ShowPills.this, ModifyPill.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("medicina", medicine);
                bundle.putSerializable("music", music);
                intent.putExtras(bundle);
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
            medicine.setNotification(cursor.getInt(4));
            listaPill.add(medicine);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();
        for(int i = 0; i < listaPill.size(); i++){
            if(listaPill.get(i).getHora() < 10){
                hora = "0" + listaPill.get(i).getHora();
            }else{
                hora = String.valueOf(listaPill.get(i).getHora());
            }
            if(listaPill.get(i).getMinutos() < 10){
                minutos = "0" + listaPill.get(i).getMinutos();
            }else{
                minutos = String.valueOf(listaPill.get(i).getMinutos());
            }
            listaInformacion.add("Name: " + listaPill.get(i).getName() + " | Nº: " + listaPill.get(i).getCantidad() + " | Time: " + hora + ":" + minutos + " | " + listaPill.get(i).getNotification());
        }
    }

    protected void addPillActivity(){
        Intent intent = new Intent(this, AddPill.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("music", music);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void backActivity(){
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (music == 1) {
            int paused = manager.time();
            manager.setPaused(paused);
            manager.pauseMusic();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (music == 1){
            manager.startMusic();
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        listViewPill = findViewById(R.id.listViewPill);
        conn = new DataBaseHelper(getApplicationContext(), "bd_medicine", null, 1);
        consultarListaMedicine();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listViewPill.setAdapter(adaptador);
    }
}