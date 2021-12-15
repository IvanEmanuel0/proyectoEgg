package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Deuda;
import com.example.proyectoEgg.entity.Gasto;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.repository.DeudaRepository;
import com.example.proyectoEgg.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DeudaService {

    @Autowired
    private DeudaRepository deudaRepository;

    @Transactional(readOnly = true)
    public List<Deuda> buscarHabilitados() {
        return deudaRepository.deudasDeAlta();
    }

    @Transactional(readOnly = true)
    public List<Deuda> buscarDeshabilitados() {
        return deudaRepository.daudasDeBaja();
    }

    @Transactional(readOnly = true)
    public Deuda buscarPorId(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
        } catch (MiException e){
            throw e;
        }
        Optional<Deuda> deudaOptional = deudaRepository.findById(id);
        return deudaOptional.orElse(null);
    }

    @Transactional
    public void crear(Categoria categoria, Double montoAPagar, String detalle) throws MiException {
        try {
            Util.esNumero(Double.toString(montoAPagar));
            deudaRepository.save(new Deuda(categoria, montoAPagar, detalle));
        } catch (MiException e){
            throw e;
        } catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void modificar(Integer id, Double montoAPagar, String detalle) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            Deuda deuda = buscarPorId(id);
            if(deuda != null){
                deuda.setId(id);
                deuda.setMontoAPagar(montoAPagar);
                deuda.setDetalle(detalle);
                deudaRepository.save(deuda);
            }
        }catch (MiException e){
            throw e;
        }catch (Exception e){
            throw e;
        }

    }

    @Transactional
    public void eliminar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            deudaRepository.deleteById(id);

        } catch (MiException e){
            throw e;
        }
    }

    @Transactional
    public void habilitar(Integer id) throws MiException {
        try {
            Util.esNumero(Integer.toString(id));
            Deuda deuda = buscarPorId(id);
            deuda.setAlta(true);
            deudaRepository.save(deuda);
        } catch (MiException e) {
            throw e;
        }
    }
    }


