package com.example.proyectoEgg.repository;

import com.example.proyectoEgg.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    @Query("SELECT r FROM Rol r WHERE r.nombre = :nombre")
    Rol buscarPorNombre(@Param("nombre") String nombre);

}
