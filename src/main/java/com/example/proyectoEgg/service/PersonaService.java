package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Cuenta;
import com.example.proyectoEgg.entity.Persona;
import com.example.proyectoEgg.entity.Rol;
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

    @Transactional
    public void crear(String nombre, String apellido, String usuario, String clave, Rol rol){
      cuentaService.crear(usuario,clave, rol);
      personaRepository.save(new Persona(nombre,apellido, cuentaService.buscarPorUsuario(usuario)));

    }

    @Transactional
    public void modificar(Integer id, String nombre, String apellido) {
        Persona persona = buscarPorId(id);
        if (persona != null) {
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            personaRepository.save(persona);
        }
    }
    @Transactional
    public void eliminar(Integer id){
             personaRepository.deleteById(id);
        }

    @Transactional
    public void habilitar(Integer id){
        Persona persona = buscarPorId(id);
        persona.setAlta(true);
        personaRepository.save(persona);

    }




}
