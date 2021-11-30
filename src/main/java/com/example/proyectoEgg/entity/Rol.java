package com.example.proyectoEgg.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
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
