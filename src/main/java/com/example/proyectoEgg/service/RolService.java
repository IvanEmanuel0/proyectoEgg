package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Rol;
import com.example.proyectoEgg.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @Transactional
    public void crear(String nombre) {
        Rol rol = new Rol();
        rol.setNombre(nombre);
        rolRepository.save(rol);
    }

    @Transactional
    public List<Rol> buscarTodos() {
        return rolRepository.findAll();
    }

    @Transactional
    public Rol buscarRolPorNombre(String nombre){
        return rolRepository.buscarPorNombre(nombre);
    }
}
