package com.example.pruebas;

import java.io.Console;
import java.util.Calendar;

public class clock {
    public int hora(){
        Calendar rigthNow = Calendar.getInstance();
        int currentHourInt24Format = rigthNow.get(Calendar.HOUR_OF_DAY);
        return currentHourInt24Format;
    }



}
