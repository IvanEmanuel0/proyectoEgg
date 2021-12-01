package com.example.proyectoEgg.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
