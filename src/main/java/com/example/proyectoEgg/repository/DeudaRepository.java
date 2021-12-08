package com.example.proyectoEgg.repository;

import com.example.proyectoEgg.entity.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Integer> {

    @Query("SELECT d FROM Deuda d WHERE d.alta=true")
    List<Deuda> deudasDeAlta();

    @Query("SELECT d FROM Deuda d WHERE d.alta=false")
    List<Deuda> daudasDeBaja();

}
