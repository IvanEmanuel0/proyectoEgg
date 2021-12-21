package com.example.proyectoEgg.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Cuenta c SET c.alta = false WHERE c.id = ?")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String usuario;
    @Column(nullable = false)
    private String clave;
    @Column(nullable = false, unique = true)
    private String correo;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Rol rol;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDate fechaDeCreacion;
    @LastModifiedDate
    private LocalDate fechaDeModificacion;
    Boolean alta;

    public Cuenta() {
        this.alta = true;
    }

    public Cuenta(String usuario, String clave, String correo, Rol rol) {
        this.usuario = usuario;
        this.clave = clave;
        this.correo = correo;
        this.rol = rol;
        this.alta = true;
    }

}
