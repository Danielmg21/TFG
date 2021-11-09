package com.example.pruebas.entidades;

import java.util.Date;

public class medicine {

    private String name;
    private int cantidad;
    private Date hora;

    public medicine(String name, int cantidad, Date hora) {
        this.name = name;
        this.cantidad = cantidad;
        this.hora = hora;
    }

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

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }
}
