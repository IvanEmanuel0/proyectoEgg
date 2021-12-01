package com.example.proyectoEgg.entity;

public class Deuda {

    //Lo va a escribir demi
    Integer id;
    Double montoAPagar;
    Boolean alta;

    public Deuda(Integer id, Double montoAPagar, Boolean alta) {
        this.id = id;
        this.montoAPagar = montoAPagar;
        this.alta = alta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMontoAPagar() {
        return montoAPagar;
    }

    public void setMontoAPagar(Double montoAPagar) {
        this.montoAPagar = montoAPagar;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }
}
