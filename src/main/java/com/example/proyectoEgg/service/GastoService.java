package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Gasto;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.repository.GastoRepository;

import com.example.proyectoEgg.utilities.Util;
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
    public void crear(Categoria categoria, Double montoPagado, String detalle) throws MiException {

        try {
            Util.esNumero(Double.toString(montoPagado));
            gastoRepository.save(new Gasto(categoria, montoPagado, detalle));
        } catch (MiException e){
            throw e;
        } catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void eliminar(Integer id) throws MiException{
        try {
            Util.esNumero(Integer.toString(id));
            gastoRepository.deleteById(id);

        } catch (MiException e){
            throw e;
        }
    }


    @Transactional
    public void modificar(Integer id, Double montoPagado, String detalle ) throws MiException{
        try {
            Util.esNumero(Integer.toString(id));
            Gasto gasto = buscarPorId(id);
            if(gasto != null){
                gasto.setId(id);
                gasto.setMontoPagado(montoPagado);
                gasto.setDetalle(detalle);
                gastoRepository.save(gasto);
            }
        }catch (MiException e){
            throw e;
        }catch (Exception e){
            throw e;
        }

    }

    @Transactional(readOnly = true)
    public Gasto buscarPorId(Integer id)throws MiException{
        try {
            Util.esNumero(Integer.toString(id));
        } catch (MiException e){
            throw e;
        }
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
    public void habilitar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            Gasto gasto = buscarPorId(id);
            gasto.setAlta(true);
            gastoRepository.save(gasto);
        } catch (MiException e) {
            throw e;
        }
    }
}

