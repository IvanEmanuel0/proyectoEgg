package com.example.proyectoEgg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class MainController {

    @GetMapping
    public ModelAndView inicio(HttpSession session){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("sesion", session.getAttribute("idSession"));
        return mav;
    }

}