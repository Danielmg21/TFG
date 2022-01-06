package com.example.pruebas;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pruebas.entidades.Medicine;
import com.example.pruebas.utilidades.MediaManager;
import com.example.pruebas.utilidades.Reminder;
import com.example.pruebas.utilidades.Utilidades;

import java.util.Calendar;
import java.util.Locale;

public class ModifyPill extends AppCompatActivity {

    DataBaseHelper conn;
    private TextView campoNombre, campoCantidad;
    private Button  eliminar, modificar, timeButton;
    private ImageButton back;
    private String nombreMedicine;
    private int hour, minute, medicineID;
    public MediaManager manager;
    public int music;
    public Medicine medicine;
    private String nombre, cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_medicine);
        campoNombre = findViewById(R.id.campoMedicine);
        campoCantidad = findViewById(R.id.campoCantidad);
        timeButton = findViewById(R.id.chooseDate);
        conn = new DataBaseHelper(getApplicationContext(),"bd_medicine", null, 1);
        Bundle extras = getIntent().getExtras();
        music = extras.getInt("music");
        if (music == 1 ){
            manager = new MediaManager(this);
            manager.startMusicModifyPill();
        }
        if(extras != null){
            medicine = (Medicine) extras.getSerializable("medicina");
            campoNombre.setText(medicine.getName());
            campoCantidad.setText(String.valueOf(medicine.getCantidad()));
            timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d", medicine.getHora(), medicine.getMinutos()));
            nombreMedicine = medicine.getName();
            medicineID = medicine.getId();
        }

        eliminar = findViewById(R.id.eliminar);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyPill.this);
                builder.setMessage("Se eliminarán todos los datos guardados, ¿Seguro que quieres continuar?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminarDatos();
                                eliminarNotification(medicineID);
                                finish();
                            }
                        }).setNegativeButton("No",null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        modificar = findViewById(R.id.modificar);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarCampos()){
                    modificarDatos(nombreMedicine);
                    modificarNotificacion(medicineID);
                    finish();
                }else{

                }
            }
        });

        back = findViewById(R.id.modifyBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });
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

    private void eliminarNotification(int id){
        Intent intent = new Intent(ModifyPill.this, Reminder.class);
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ModifyPill.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private void modificarNotificacion(int id){
        Intent intent = new Intent(ModifyPill.this, Reminder.class);
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ModifyPill.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 60 * 60 *24, pendingIntent);
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, medicine.getHora(), medicine.getMinutos(), true);
        timePickerDialog.show();
    }
    public boolean comprobarCampos(){
        nombre = campoNombre.getText().toString();
        cantidad = campoCantidad.getText().toString();
        if(nombre.matches("") || cantidad.matches("")){
            Toast.makeText(this, "Porfavor rellena los campos requeridos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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