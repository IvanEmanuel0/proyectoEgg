package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Cuenta;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ModelAndView mostrarTodos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("cuenta-lista");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("cuentas", cuentaService.buscarHabilitados());
        return mav;
    }

    @GetMapping("/deshabilitados")
    public ModelAndView mostrarDeshabilitados(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("cuenta-lista");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("cuentas", cuentaService.buscarDeshabilitados());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearCuenta() {
        ModelAndView mav = new ModelAndView("cuenta-formulario");
        mav.addObject("cuenta", new Cuenta());
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarCuenta(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("cuenta-formulario");

        mav.addObject("cuenta", cuentaService.buscarPorId(id));
        mav.addObject("titulo", "Editar Cuenta");
        mav.addObject("accion", "modificar");


        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String usuario, @RequestParam String clave, RedirectAttributes redirectAttributes){
        RedirectView rv = new RedirectView("/login");
        cuentaService.crear(usuario, clave);
        return rv;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam String usuario, @RequestParam String clave, RedirectAttributes redirectAttributes) {
        cuentaService.modificar(id, usuario, clave);
        return new RedirectView("/cuentas");
    }

    @PostMapping("eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        cuentaService.eliminar(id);
        return new RedirectView("/cuentas");
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        cuentaService.habilitar(id);
        return new RedirectView("/cuentas/deshabilitados");
    }

}
