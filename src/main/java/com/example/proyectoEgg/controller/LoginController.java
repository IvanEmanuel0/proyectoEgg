package com.example.proyectoEgg.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("login", true);
        if(error != null) {
            mav.addObject("error", "Usuario o contraseña invalidos.");
        }

        if(logout != null) {
            mav.addObject("logout", "Ha salido correctamente de la plataforma.");
        }

        if(principal != null) {
            mav.setViewName("redirect:/");
        }

        return mav;

    }

}
