package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Cuenta;
import com.example.proyectoEgg.entity.Rol;
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
        try {
            Cuenta cuenta = cuentaService.buscarPorId(id);
            mav.addObject("cuenta", cuenta);
        } catch (MiException e) {
            mav.addObject("error", e.getMessage());
        }

        // mav.addObject("titulo", "Editar Cuenta");
        // mav.addObject("accion", "modificar");


        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String usuario, @RequestParam String clave, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/login");
        try {
            cuentaService.crear(usuario, clave);
            redirectAttributes.addFlashAttribute("exito", "La cuenta fue creada correctamente.");
        } catch(MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("cuenta", usuario);
            redirectView.setUrl("/cuentas/crear");
        }

        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam String usuario, @RequestParam String clave, @RequestParam Rol rol, RedirectAttributes redirectAttributes) {
        try {
            cuentaService.modificar(id, usuario, clave);
            redirectAttributes.addFlashAttribute("exito", "La cuenta se modifico correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/cuentas");
    }

    @PostMapping("eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            cuentaService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "La cuenta fue eliminada correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/cuentas");
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            cuentaService.habilitar(id);
            redirectAttributes.addFlashAttribute("exito", "La cuenta se reactivo correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/cuentas/deshabilitados");
    }

}
