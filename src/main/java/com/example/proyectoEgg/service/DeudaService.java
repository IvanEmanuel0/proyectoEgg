package com.example.proyectoEgg.service;

import com.example.proyectoEgg.entity.Deuda;
import com.example.proyectoEgg.repository.DeudaRepository;
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
    public Deuda buscarPorId(Integer id) {
        Optional<Deuda> deudaOptional = deudaRepository.findById(id);
        return deudaOptional.orElse(null);
    }

    @Transactional
    public void crear(Double montoAPagar, String detalle) {
        deudaRepository.save(new Deuda(montoAPagar, detalle));
    }

    @Transactional
    public void modificar(Integer id, Double montoAPagar, String detalle) {
        Deuda deuda = buscarPorId(id);
        if(deuda != null){
            deuda.setMontoAPagar(montoAPagar);
            deuda.setDetalle(detalle);
            deudaRepository.save(deuda);
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        Deuda deuda = buscarPorId(id);
        if(deuda != null) {
            deudaRepository.deleteById(id);
        }
    }

    @Transactional
    public void habilitar(Integer id) {
        Deuda deuda = buscarPorId(id);
        deuda.setAlta(true);
        deudaRepository.save(deuda);
    }

}
