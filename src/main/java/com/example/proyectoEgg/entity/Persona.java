package com.example.proyectoEgg.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
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
@SQLDelete(sql = "UPDATE Persona p SET p.alta = false WHERE p.id = ?")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    private Double montoDisponible;
    @OneToOne
    private Cuenta cuenta;
    @OneToMany(mappedBy = "persona")
    private List<Categoria> listaDeCategorias;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;
    @LastModifiedDate
    private LocalDate fechaDeModificacion;
    private String imagen;

    @Column(nullable = false)
    private Boolean alta;


    public Persona(){
        this.alta = true;
    }

    public Persona(String nombre, String apellido, Cuenta cuenta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.montoDisponible = 0.0;
        this.cuenta = cuenta;
        this.alta = true;
    }
}
