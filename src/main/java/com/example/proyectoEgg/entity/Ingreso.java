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
public class Ingreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Double montoIngresado;
    @JoinColumn(nullable = false)
    private Boolean alta;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;
    @LastModifiedDate
    private LocalDate fechaDeModificacion;


    public Ingreso() {
    }

    public Ingreso(Integer id, Double montoIngresado, Boolean alta, LocalDate fechaDeCreacion, LocalDate fechaDeModificacion) {
        this.id = id;
        this.montoIngresado = montoIngresado;
        this.alta = alta;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
    }
}
