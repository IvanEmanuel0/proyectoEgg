package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class RolController {

    @Autowired
    private RolService rolService;
}
