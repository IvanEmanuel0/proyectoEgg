package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.service.CategoriaService;
import com.example.proyectoEgg.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class MainController {

    @Autowired
    private PersonaService personaService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ModelAndView inicio(HttpSession session){
        ModelAndView mav = new ModelAndView("index");
        try {
            mav.addObject("persona", personaService.buscarPorCuenta((Integer)session.getAttribute("idSession")));
            mav.addObject("plata", personaService.calcularDineroDisponible(categoriaService.buscarHabilitados(personaService.buscarPorCuenta((Integer)session.getAttribute("idSession")))));
        } catch(MiException e){
            System.out.println("error");
        }
        mav.addObject("sesion", session.getAttribute("idSession"));
        return mav;
    }

}