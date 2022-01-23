package com.example.pruebas.entidades;

import java.io.Serializable;

public class Medicine implements Serializable {

    private String name;
    private int cantidad;
    private int hora;
    private int minutos;
    private int id;

    public Medicine(String name, int cantidad, int hora, int minutos, int id) {
        this.name = name;
        this.cantidad = cantidad;
        this.hora = hora;
        this.minutos = minutos;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
