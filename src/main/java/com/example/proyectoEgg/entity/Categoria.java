package com.example.proyectoEgg.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "categoria")
    private List<Gasto> listaDeGastos;
    @OneToMany(mappedBy = "categoria")
    private List<Ingreso> listaDeIngresos;
    @OneToMany(mappedBy = "categoria")
    private List<Deuda> listaDeDeudas;
    private Boolean alta;
    @ManyToOne
    private Persona persona;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;
    @LastModifiedDate
    private LocalDate fechaDeModificacion;


    public Categoria() {
        this.listaDeGastos = new ArrayList<>();
        this.listaDeIngresos = new ArrayList<>();
        this.listaDeDeudas = new ArrayList<>();
        this.alta = true;
    }

    public Categoria(String nombre){
        this.nombre = nombre;
        this.listaDeGastos = new ArrayList<>();
        this.listaDeIngresos = new ArrayList<>();
        this.listaDeDeudas = new ArrayList<>();
        this.alta = true;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

