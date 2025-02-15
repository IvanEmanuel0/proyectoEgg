package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Ingreso;
import com.example.proyectoEgg.entity.Persona;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.repository.CategoriaRepository;
import com.example.proyectoEgg.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;



    @Transactional
    public Categoria buscarPorId(Integer id) throws MiException{

        try{
            Util.esNumero(Integer.toString(id));
        }catch(MiException e){
            throw e;
        }
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        return categoriaOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Categoria> buscarHabilitados(Persona persona) {
        return categoriaRepository.categoriasDeAlta(persona);
    }


    @Transactional(readOnly = true)
    public List<Categoria> BuscarDeshabilitados(){
        return categoriaRepository.categoriasDeBaja();
    }
    ///---------

    public void crear(String nombre, Persona persona) throws MiException{
        try{
        Util.sonLetras(nombre);
            Categoria c = new Categoria();
            c.setNombre(nombre);
            c.setPersona(persona);
            categoriaRepository.save(c);
        }catch(MiException e){
            throw e;
        }

    }

    @Transactional
    public void modificar(Integer id, String nombre) throws MiException{
        try{
            Util.esNumero(Integer.toString(id));
            Util.sonLetras(nombre);
            Categoria c = buscarPorId(id);
            c.setNombre(nombre);
            categoriaRepository.save(c);
        }catch(MiException e){
            throw e;
        }



    }

    @Transactional
    public void eliminar(Integer id) throws MiException{
        try{
            Util.esNumero(Integer.toString(id));
            categoriaRepository.deleteById(id);
        }catch(MiException e){
            throw e;
        }


    }

    @Transactional
    public void habilitar(Integer id) throws MiException{

        try{
            Util.esNumero(Integer.toString(id));
            Categoria i = buscarPorId(id);
            i.setAlta(true);
            categoriaRepository.save(i);
        }catch(MiException e){
            throw e;
        }


    }

    /*
    @Transactional
    public void guardar(Categoria categoria){
        categoriaRepository.save(categoria);
    }
    */

    @Transactional
    public void crearCategoriasBase(Persona persona) {
        try {
            crear("Servicios", persona);
            crear("Entretenimiento", persona);
            crear("Alimentación", persona);
            crear("Educación", persona);
            crear("Viáticos", persona);
            crear("Cuidado personal", persona);
            crear("Salud", persona);
        } catch(MiException e){
            System.out.println(e.getMessage());
        }
    }

}
