package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Gasto;
import com.example.proyectoEgg.repository.GastoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;


    @Transactional
    public void crear(Double montoPagado, String detalle){
        Gasto gasto = new Gasto();

        gasto.setMontoPagado(montoPagado);
        gasto.setDetalle(detalle);
        gastoRepository.save(gasto);
    }

    @Transactional
    public void eliminar(Integer id){
        gastoRepository.deleteById(id);
    }


    @Transactional
    public void modificar(Integer id, Double montoPagado, String detalle ){
        Gasto gasto = buscarPorId(id);
        if(gasto != null){
            gasto.setId(id);
            gasto.setMontoPagado(montoPagado);
            gasto.setDetalle(detalle);
        }

    }

    @Transactional(readOnly = true)
    public Gasto buscarPorId(Integer id){
        Optional<Gasto> optionalGasto = gastoRepository.findById(id);
        return optionalGasto.orElse(null);
    }


    @Transactional(readOnly = true)
    public List<Gasto> buscarHabilitados(){

        return gastoRepository.gastosDeAlta();
    }


    @Transactional(readOnly = true)
    public List<Gasto> BuscarDeshabilitados(){

        return gastoRepository.gastosDeBaja();
    }

    @Transactional
    public void habilitar(Integer id) {

        Gasto gasto = buscarPorId(id);
        gasto.setAlta(true);
        gastoRepository.save(gasto);

    }




}
