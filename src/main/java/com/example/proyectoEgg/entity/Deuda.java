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
@SQLDelete(sql = "UPDATE Deuda d SET d.alta = false WHERE d.id = ?")
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Double montoAPagar;
    private String detalle;
    private Boolean alta;
    @ManyToOne
    private Categoria categoria;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;
    @LastModifiedDate
    private LocalDate fechaDeModificacion;


    public Deuda() {
        this.alta = true;
    }

    public Deuda(Categoria categoria, Double montoAPagar, String detalle) {
        this.categoria = categoria;
        this.montoAPagar = montoAPagar;
        this.detalle = detalle;
        this.alta = true;
    }
}
