package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Cuenta;
import com.example.proyectoEgg.entity.Persona;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.service.CategoriaService;
import com.example.proyectoEgg.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

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
            Integer idCuenta = (Integer)session.getAttribute("idSession");
            Persona persona = personaService.buscarPorCuenta(idCuenta);
            List<Categoria> categorias = categoriaService.buscarHabilitados(persona);
            mav.addObject("persona", persona);
            mav.addObject("dineroDisponible", personaService.calcularDineroDisponible(categorias));
            mav.addObject("gastoTotal", personaService.calcularTotalGastos(categorias));
            mav.addObject("ingresoTotal", personaService.calcularTotalIngresos(categorias));
        } catch(MiException e){
            System.out.println("error");
        }
        mav.addObject("sesion", session.getAttribute("idSession"));
        return mav;
    }

}