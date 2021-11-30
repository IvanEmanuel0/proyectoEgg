package com.example.proyectoEgg.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    @ManyToOne
    private Categoria categoria;
    private Boolean alta;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;
    @LastModifiedDate
    private LocalDate fechaDeModificacion;


    public Ingreso() {
    }

    public Ingreso(Integer id, Double montoIngresado, Categoria categoria, Boolean alta, LocalDate fechaDeCreacion, LocalDate fechaDeModificacion) {
        this.id = id;
        this.montoIngresado = montoIngresado;
        this.categoria = categoria;
        this.alta = alta;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
    }
}
