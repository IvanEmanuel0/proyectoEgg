package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;
}