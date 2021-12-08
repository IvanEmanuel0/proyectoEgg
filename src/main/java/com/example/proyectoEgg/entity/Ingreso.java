package com.example.proyectoEgg.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Ingreso i SET i.alta = false WHERE i.id = ?")
public class Ingreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Double montoIngresado;
    @Column(nullable = false)
    private String detalle;
    @JoinColumn(nullable = false)
    private Boolean alta;
    @ManyToOne
    private Categoria categoria;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;
    @LastModifiedDate
    private LocalDate fechaDeModificacion;


    public Ingreso() {
        this.alta = true;
    }

    public Ingreso(Double montoIngresado, String detalle) {
        this.montoIngresado = montoIngresado;
        this.detalle = detalle;
        this.alta = true;
    }
}