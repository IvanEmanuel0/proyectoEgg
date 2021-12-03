package com.example.proyectoEgg.repository;

import com.example.proyectoEgg.entity.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Integer> {

@Query("SELECT g FROM Gasto g WHERE g.alta = true")
List<Gasto> gastosDeAlta();

@Query("SELECT g FROM Gasto g WHERE g.alta=false")
    List<Gasto> gastosDeBaja();

}
