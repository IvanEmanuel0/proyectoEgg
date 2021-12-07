package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Categoria;
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

    @Transactional(readOnly = true)
    public void crear(String nombre, List listaDeGastos, List listaDeIngresos,List listaDeDeudas){
        Categoria i = new Categoria();
        i.setNombre(nombre);
        i.setListaDeGastos(listaDeGastos);
        i.setListaDeIngresos(listaDeIngresos);
        i.setListaDeDeudas(listaDeDeudas);
        categoriaRepository.save(i);
    }
    @Transactional
    public void modificar(Integer id, String nombre,List listaDeGastos, List listaDeIngresos,List listaDeDeudas){
        Categoria i = buscarPorId(id);

        i.setNombre(nombre);
        i.setListaDeGastos(listaDeGastos);
        i.setListaDeIngresos(listaDeIngresos);
        i.setListaDeDeudas(listaDeDeudas);
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

}
