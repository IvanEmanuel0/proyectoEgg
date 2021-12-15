package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Rol;
import com.example.proyectoEgg.service.CuentaService;
import com.example.proyectoEgg.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearRol(HttpServletRequest request)  {
        ModelAndView mav = new ModelAndView("rol-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("rol", flashMap.get("rol"));
        } else {
            mav.addObject("rol", new Rol());
        }
        mav.addObject("accion", "guardar");
        mav.addObject("rol", new Rol());
        mav.addObject("cuentas", cuentaService.buscarHabilitados());
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardar(@RequestParam String nombre) {
        rolService.crear(nombre);
        return new RedirectView("/ingresos");
    }
}
