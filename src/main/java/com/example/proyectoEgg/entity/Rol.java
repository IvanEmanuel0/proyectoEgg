package com.example.proyectoEgg.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Rol SET alta = false WHERE id = ?")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Boolean alta;

    public Rol() {
        this.alta = true;
    }

    public Rol(Integer id, String nombre, Boolean alta) {

        this.nombre = nombre;
        this.alta = true;
    }
}
