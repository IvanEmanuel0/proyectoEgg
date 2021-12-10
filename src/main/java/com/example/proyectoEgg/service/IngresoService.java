package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Ingreso;
import com.example.proyectoEgg.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Transactional
    public Ingreso buscarPorId(Integer id){
        Optional<Ingreso> ingresoOptional = ingresoRepository.findById(id);
        return ingresoOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Ingreso> buscarHabilitados(){
        return ingresoRepository.IngresosDeAlta();
    }

    @Transactional(readOnly = true)
    public List<Ingreso> buscarDeshabilitados(){
        return ingresoRepository.IngresosDeBaja();
    }

    @Transactional
    public Ingreso crear(Double montoIngresado, String detalle){
        Ingreso i = new Ingreso();

        i.setMontoIngresado(montoIngresado);
        i.setDetalle(detalle);
        ingresoRepository.save(i);

        return i;
    }

    @Transactional
    public void modificar(Integer id, Double montoIngresado, String detalle){
        Ingreso i = buscarPorId(id);

        i.setMontoIngresado(montoIngresado);
        i.setDetalle(detalle);
        ingresoRepository.save(i);
    }

    @Transactional
    public void eliminar(Integer id){
        ingresoRepository.deleteById(id);
    }

    @Transactional
    public void habilitar(Integer id){
        Ingreso i = buscarPorId(id);
        i.setAlta(true);
        ingresoRepository.save(i);
    }

    @Transactional
    public void agregarIngreso(Categoria categoria, Double montoIngresado, String detalle){

        Categoria c = categoriaService.buscarPorId(categoria.getId());
        List<Ingreso> ingresos = c.getListaDeIngresos();
        ingresos.add(crear(montoIngresado, detalle));
        c.setListaDeIngresos(ingresos);
        categoriaService.guardar(c);

    }

}
