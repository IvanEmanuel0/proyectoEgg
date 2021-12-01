package com.example.proyectoEgg.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)

public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double montoPagado;

    private Boolean alta;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;

    @LastModifiedDate
    private LocalDate fechaDeModificacion;

    public Gasto() {
    }

    public Gasto(Integer id, Double montoPagado, Boolean alta, LocalDate fechaDeCreacion, LocalDate fechaDeModificacion) {
        this.id = id;
        this.montoPagado = montoPagado;
        this.alta = alta;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
    }
}