package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.*;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.repository.PersonaRepository;
import com.example.proyectoEgg.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private IngresoService ingresoService;
    @Autowired
    private GastoService gastoService;
    @Autowired
    private FotoService fotoService;

    @Transactional(readOnly = true)
    public List<Persona> buscarHabilitados(){
        return personaRepository.personasDeAlta();
    }

    @Transactional(readOnly = true)
        public List<Persona> buscarDeshabilitados(){
            return personaRepository.personasDeBaja();
        }

    @Transactional(readOnly = true)
    public Persona buscarPorId(Integer id) throws MiException{
        try {
            Util.esNumero(Integer.toString(id));
        } catch (MiException e) {
            throw e;
        }
        Optional<Persona> optionalPersona = personaRepository.findById(id);
        return optionalPersona.orElse(null);
    }

    @Transactional(readOnly = true)
    public Persona buscarPorCuenta(Integer id) throws MiException{
        return personaRepository.buscarPersonaPorCuenta(cuentaService.buscarPorId(id));
    }


    @Transactional
    public void crear(String nombre, String apellido, String usuario, String clave, String correo , MultipartFile foto) throws MiException{

        try {
            Util.sonLetras(nombre);
            Util.sonLetras(apellido);

            Persona p = new Persona();
            p.setNombre(nombre);
            p.setApellido(apellido);

            if(!foto.isEmpty()){
                p.setImagen(fotoService.copiar(foto));
            }

            cuentaService.crear(usuario,clave, correo);
            p.setCuenta(cuentaService.buscarPorUsuario(usuario));

            personaRepository.save(p);

        } catch (MiException e){
            throw e;
        } catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void modificar(Integer id, String nombre, String apellido, MultipartFile foto, String clave) throws MiException {
        try {
            Util.sonLetras(nombre);
            Util.sonLetras(apellido);
            Persona persona = buscarPorId(id);
            if(persona != null){
                persona.setId(id);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                if(!foto.isEmpty()){
                    persona.setImagen(fotoService.copiar(foto));
                }
                cuentaService.modificar(id, clave);
                personaRepository.save(persona);
            }
        }catch (MiException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void eliminar(Integer id) throws MiException{
        try {
            Util.esNumero(Integer.toString(id));
            personaRepository.deleteById(id);
        } catch (MiException e){
            throw e;
        }
    }

    @Transactional
    public void habilitar(Integer id) throws MiException{
        try {
            Util.esNumero(Integer.toString(id));
            Persona persona = buscarPorId(id);
            persona.setAlta(true);
            personaRepository.save(persona);
        } catch (MiException e) {
            throw e;
        }
    }

    @Transactional
    public Double calcularDineroDisponible(List<Categoria> categorias){
        return  calcularTotalIngresos(categorias)-calcularTotalGastos(categorias);
    }

    @Transactional
    public Double calcularTotalIngresos(List<Categoria> categorias){
        return ingresoService.calcularTotalIngresos(categorias);
    }

    @Transactional
    public Double calcularTotalGastos(List<Categoria> categorias){
        return gastoService.calcularTotalGastos(categorias);
    }
}
