package com.example.proyectoEgg.service;

import com.example.proyectoEgg.repository.GastoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;
}
