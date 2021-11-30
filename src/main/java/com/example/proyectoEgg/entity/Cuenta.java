package com.example.proyectoEgg.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Cuenta {

    Integer id;
    String usuario;
    String clave;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;
    @LastModifiedDate
    private LocalDate fechaDeModificacion;
    Boolean alta;

    public Cuenta() {
    }

    public Cuenta(Integer id, String usuario, String clave, LocalDate fechaDeCreacion, LocalDate fechaDeModificacion, Boolean alta) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
        this.alta = alta;
    }
}
