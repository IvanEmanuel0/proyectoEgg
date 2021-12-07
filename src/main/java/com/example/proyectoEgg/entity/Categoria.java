package com.example.proyectoEgg.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    private List<Gasto> listaDeGastos;
    private List<Ingreso> listaDeIngresos;
    private List<Deuda> listaDeDeudas;
    private Boolean alta;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;
    @LastModifiedDate
    private LocalDate fechaDeModificacion;


    public Categoria() {
    }

    public Categoria(Integer id, String nombre, List<Gasto> listaDeGastos, List<Ingreso> listaDeIngresos, List<Deuda> listaDeDeudas, LocalDate fechaDeCreacion, LocalDate fechaDeModificacion){
        this.id = id;
        this.nombre = nombre;
        this.listaDeGastos = listaDeGastos;
        this.listaDeIngresos = listaDeIngresos;
        this.listaDeDeudas = listaDeDeudas;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
    }
}

