package com.example.proyectoEgg.repository;

import com.example.proyectoEgg.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

    Optional<Cuenta> findByUsuario(String usuario);

    @Query("SELECT c FROM Cuenta c WHERE c.usuario = :usuario")
    Cuenta buscarCuentaPorUsuario(@Param("usuario") String usuario);

    @Query("SELECT c FROM Cuenta c WHERE c.alta = true")
    List<Cuenta> usuariosHabilitados();

    @Query("SELECT c FROM Cuenta c WHERE c.alta = false")
    List<Cuenta> usuariosDeshabilitados();

    boolean existsCuentaByUsuario(String usuario);

}
