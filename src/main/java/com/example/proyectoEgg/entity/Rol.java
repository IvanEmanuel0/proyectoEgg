package com.example.proyectoEgg.entity;

import javax.persistence.Entity;

@Entity
public class Rol {

    Integer id;
    String nombre;
    Boolean alta;

    public Rol() {
    }

    public Rol(Integer id, String nombre, Boolean alta) {
        this.id = id;
        this.nombre = nombre;
        this.alta = alta;
    }
}
