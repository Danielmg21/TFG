package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.pruebas.utilidades.MediaManager;
import com.example.pruebas.utilidades.Reminder;
import com.example.pruebas.utilidades.Utilidades;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;


public class AddPill extends AppCompatActivity {

    Button timeButton;
    String nombreMedicina, cantidadMedicina;
    ImageButton cancel, save;
    EditText nombre,cantidad;
    int hour, minute, random;

    public MediaManager manager;
    public int music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);
        nombre = findViewById(R.id.cantidad1);
        cantidad = findViewById(R.id.cantidad);
        timeButton = findViewById(R.id.chooseDate);
        Bundle extras = getIntent().getExtras();
        music = extras.getInt("music");
        if (music == 1 ){
            manager = new MediaManager(this);
            manager.startMusicModifyPill();
        }

        //Método para cancelar la creacion de la pastilla
        cancel = findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddPill.this);
                builder.setMessage("No se guardarán los cambios realizados, ¿Seguro que quieres continuar?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
                if (comprobarCampos()){
                    registrarMedicine();
                    registerNotification();
                    finish();
                }
            }
        });
    }
    public boolean comprobarCampos(){
        nombreMedicina = nombre.getText().toString();
        cantidadMedicina = cantidad.getText().toString();
        if(nombreMedicina.matches("") || cantidadMedicina.matches("")){
            Toast.makeText(this, "Porfavor rellena los campos requeridos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {
                    hour = selectHour;
                    minute = selectMinute;
                    timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d", selectHour, selectMinute));
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, 0, 0, true);
        timePickerDialog.show();
    }

    public void registerNotification(){
        Intent intent = new Intent(AddPill.this, Reminder.class);
        intent.putExtra("id", random);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddPill.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 60 * 60 *24, pendingIntent);
    }

    private void registrarMedicine(){
        DataBaseHelper helper = new DataBaseHelper(AddPill.this, "bd_medicine",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        random = new Random().nextInt(101);
        values.put(Utilidades.CAMPO_NAME,nombreMedicina);
        values.put(Utilidades.CAMPO_CANTIDAD,cantidadMedicina);
        values.put(Utilidades.CAMPO_HORA, hour);
        values.put(Utilidades.CAMPO_MINUTOS, minute);
        values.put(Utilidades.CAMPO_NOTIFICATION, random);
        Long idResultado = db.insert(Utilidades.TABLE_MEDICINE,Utilidades.CAMPO_NAME,values);
        Toast.makeText(getApplicationContext(),"Id registro"+idResultado,Toast.LENGTH_SHORT).show();
        db.close();
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
}