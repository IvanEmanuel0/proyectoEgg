package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ModelAndView mostrarTodos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("cuentas", cuentaService.buscarHabilitados());
        return mav;
    }

    @GetMapping
    public ModelAndView mostrarDeshabilitados(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("cuentas", cuentaService.buscarDeshabilitados());
        return mav;
    }



}
