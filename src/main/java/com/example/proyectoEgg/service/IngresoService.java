package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Ingreso;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.repository.IngresoRepository;
import com.example.proyectoEgg.utilities.Util;
import javafx.scene.chart.ScatterChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;


    @Transactional
    public Ingreso buscarPorId(Integer id) throws MiException {

        try{
            Util.esNumero(Integer.toString(id));
        }catch(MiException e){
            throw e;
        }
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
    public void crear(Categoria categoria, Double montoIngresado, String detalle) throws MiException{
        try{
            Util.esNumero(Double.toString(montoIngresado));
            Ingreso i = new Ingreso();
            i.setCategoria(categoria);
            i.setMontoIngresado(montoIngresado);
            i.setDetalle(detalle);
            ingresoRepository.save(i);
        }catch(MiException e){
            throw e;
        }


    }

    @Transactional
    public void modificar(Integer id, Double montoIngresado, String detalle) throws MiException{
        try{
            Util.esNumero(Integer.toString(id));
            Ingreso i = buscarPorId(id);
            i.setMontoIngresado(montoIngresado);
            i.setDetalle(detalle);
            ingresoRepository.save(i);
        }catch(MiException e){
            throw e;
        }

    }

    @Transactional
    public void eliminar(Integer id) throws MiException{
        try{
            Util.esNumero(Integer.toString(id));
            ingresoRepository.deleteById(id);
        }catch(MiException e){
            throw e;
        }catch(Exception e){
            throw e;
        }

    }

    @Transactional
    public void habilitar(Integer id) throws MiException{
        try{
            Util.esNumero(Integer.toString(id));
            Ingreso i = buscarPorId(id);
            i.setAlta(true);
            ingresoRepository.save(i);
        }catch(MiException e){
            throw e ;
        }catch(Exception e){
            throw e;
        }


    }

}
