package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Cuenta;
import com.example.proyectoEgg.entity.Persona;
import com.example.proyectoEgg.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private CuentaService cuentaService;

    @Transactional(readOnly = true)
    public List<Persona> buscarHabilitados(){
        return personaRepository.personasDeAlta();
    }

    @Transactional(readOnly = true)
        public List<Persona> buscarDeshabilitados(){
            return personaRepository.personasDeBaja();
        }

    @Transactional(readOnly = true)
    public Persona buscarPorId(Integer id){
        Optional<Persona> optionalPersona = personaRepository.findById(id);
        return optionalPersona.orElse(null);
    }

    @Transactional(readOnly = true)
    public void crear(String nombre, String apellido, Double montoDisponible, String usuario, String clave, List<Categoria> listaDeCategorias, String imagen){
      cuentaService.crear(usuario,clave);
      personaRepository.save(new Persona(nombre,apellido, montoDisponible, cuentaService.buscarPorUsuario(usuario), listaDeCategorias, imagen));

    }

    @Transactional(readOnly = true)
    public void modificar(Integer id, String nombre, String apellido, Double montoDisponible, List<Categoria> listaDeCategorias, String imagen) {
        Persona persona = buscarPorId(id);
        if (persona != null) {
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setMontoDisponible(montoDisponible);
            persona.setListaDeCategorias(listaDeCategorias);
            persona.setImagen(imagen);
            personaRepository.save(persona);
        }
    }
    @Transactional(readOnly = true)
    public void eliminar(Integer id){
             personaRepository.deleteById(id);
        }

    @Transactional(readOnly = true)
    public void habilitar(Integer id){
        Persona persona = buscarPorId(id);
        persona.setAlta(true);
        personaRepository.save(persona);

    }




}
