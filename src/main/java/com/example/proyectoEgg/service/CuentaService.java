package com.example.proyectoEgg.service;

import com.example.proyectoEgg.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

}
