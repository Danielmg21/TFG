package com.example.pruebas.utilidades;

public class Utilidades {

    public static final String TABLE_MEDICINE = "medicine";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NAME = "nombre";
    public static final String CAMPO_CANTIDAD = "cantidad";
    public static final String CAMPO_HORA = "hora";
    public static final String CAMPO_MINUTOS = "minutos";


    public static final String CREAR_TABLA_MEDICINE = "CREATE TABLE " +
             TABLE_MEDICINE + " ("
            + CAMPO_ID + " INTEGER, "
            + CAMPO_NAME + " TEXT, "
            + CAMPO_CANTIDAD + " INTEGER, "
            + CAMPO_HORA + " INTEGER, "
            + CAMPO_MINUTOS + "INTEGER)";

}
