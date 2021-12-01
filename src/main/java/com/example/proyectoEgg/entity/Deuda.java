package com.example.proyectoEgg.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)

public class Deuda {

    //Lo va a escribir demi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(nullable = false)
    Double montoAPagar;
    Boolean alta;

    public Deuda(Integer id, Double montoAPagar, Boolean alta) {
        this.id = id;
        this.montoAPagar = montoAPagar;
        this.alta = alta;
    }

    public Deuda() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMontoAPagar() {
        return montoAPagar;
    }

    public void setMontoAPagar(Double montoAPagar) {
        this.montoAPagar = montoAPagar;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }
}
