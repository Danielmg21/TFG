package com.example.pruebas.entidades;

import java.util.Date;

public class Medicine {

    private String name;
    private int cantidad;
    private int hora;
    private int minutos;
    private String descripcion;

    public Medicine(String name, int cantidad, int hora, int minutos) {
        this.name = name;
        this.cantidad = cantidad;
        this.hora = hora;
        this.minutos = minutos;
    }

    public Medicine() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
