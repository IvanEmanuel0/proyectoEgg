package com.example.proyectoEgg.repository;

import com.example.proyectoEgg.entity.Categoria;
//import com.example.proyectoEgg.entity.Deuda;
import com.example.proyectoEgg.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    @Query("SELECT c FROM Categoria c WHERE c.alta=true AND c.persona = :persona")
    List<Categoria> categoriasDeAlta(Persona persona);

    @Query("SELECT c FROM Categoria c WHERE c.alta=false")
    List<Categoria> categoriasDeBaja();
}
