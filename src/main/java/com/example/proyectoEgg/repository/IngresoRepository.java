package com.example.proyectoEgg.repository;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Integer>{

    @Query("SELECT i FROM Ingreso i WHERE i.alta = true AND categoria = :categoria")
    List<Ingreso> ingresosDeAlta(@Param("categoria") Categoria categoria);

    @Query("SELECT i FROM Ingreso i WHERE i.alta = false")
    List<Ingreso> ingresosDeBaja();
}
