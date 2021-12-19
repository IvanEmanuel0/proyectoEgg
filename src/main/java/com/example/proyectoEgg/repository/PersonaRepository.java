package com.example.proyectoEgg.repository;

import com.example.proyectoEgg.entity.Cuenta;
import com.example.proyectoEgg.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    @Query("SELECT p FROM Persona p WHERE p.alta=true")
    List<Persona> personasDeAlta();

    @Query("SELECT p FROM Persona p WHERE p.alta=false")
    List<Persona> personasDeBaja();

    @Query("SELECT p FROM Persona p WHERE p.cuenta = :cuenta")
    Persona buscarPersonaPorCuenta(@Param("cuenta") Cuenta cuenta);


}
