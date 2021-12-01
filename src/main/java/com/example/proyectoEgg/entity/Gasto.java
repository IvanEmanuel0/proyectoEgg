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
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Double montoPagado;

    @Column(nullable = false)
    Categoria categoria;

    private Boolean alta;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;

    @LastModifiedDate
    private LocalDate fechaDeModificacion;

}