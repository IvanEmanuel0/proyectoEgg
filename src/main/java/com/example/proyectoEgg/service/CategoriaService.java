package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Ingreso;
import com.example.proyectoEgg.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria buscarPorId(Integer id){
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        return categoriaOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Categoria> buscarHabilitados() {
        return categoriaRepository.categoriasDeAlta();
    }


    @Transactional(readOnly = true)
    public List<Categoria> BuscarDeshabilitados(){
        return categoriaRepository.categoriasDeBaja();
    }
    ///---------

    public void crear(String nombre){
        Categoria i = new Categoria();
        i.setNombre(nombre);
        categoriaRepository.save(i);
    }
    @Transactional
    public void modificar(Integer id, String nombre){
        Categoria i = buscarPorId(id);

        i.setNombre(nombre);
        categoriaRepository.save(i);
    }
    @Transactional
    public void eliminar(Integer id){
        Categoria categoria = buscarPorId(id);
        if(categoria != null) {
            categoriaRepository.deleteById(id);
        }
    }

    @Transactional
    public void habilitar(Integer id){
        Categoria i = buscarPorId(id);
        i.setAlta(true);

        categoriaRepository.save(i);
    }

    @Transactional
    public void guardar(Categoria categoria){
        categoriaRepository.save(categoria);
    }



}
