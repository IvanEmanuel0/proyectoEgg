package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Deuda;
import com.example.proyectoEgg.entity.Gasto;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.service.CategoriaService;
import com.example.proyectoEgg.service.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/deudas")
public class DeudaController {

    @Autowired
    private DeudaService deudaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ModelAndView mostrarTodos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("deuda-lista");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito",flashMap.get("exito"));
            mav.addObject("error",flashMap.get("error"));
        }

        mav.addObject("accion", "eliminar");
        mav.addObject("titulo", "Lista de Deudas");
        mav.addObject("deudas", deudaService.buscarHabilitados());

        return mav;
    }

    @GetMapping("/deshabilitados")
    public ModelAndView mostrarDeshabilitados(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("deuda-lista");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("accion", "habilitar");
        mav.addObject("titulo", "Libros de Baja");
        mav.addObject("deudas", deudaService.buscarDeshabilitados());

        return mav;

    }

    @GetMapping("/crear")
    public ModelAndView crearDeuda() {
        ModelAndView mav = new ModelAndView("deuda-formulario");
        mav.addObject("deuda", new Deuda());
        mav.addObject("categorias", categoriaService.buscarHabilitados());
        mav.addObject("titulo", "Crear Deuda");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarDeuda(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("deuda-formulario");
        try {
            Deuda deuda = deudaService.buscarPorId(id);
            mav.addObject("deuda", deuda);
        } catch(MiException e) {
            mav.addObject("error", e.getMessage());
        }
        mav.addObject("titulo", "Editar Deuda");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Categoria categoria, @RequestParam Double montoAPagar, @RequestParam String detalle, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/deudas");
        try {
            deudaService.crear(categoria, montoAPagar, detalle);
            redirectAttributes.addFlashAttribute("éxito", "La deuda se registró correctatamente.");

        }catch (MiException e){
            redirectAttributes.addFlashAttribute("categoria", categoria);
            redirectAttributes.addFlashAttribute("montoAPagar", montoAPagar);
            redirectAttributes.addFlashAttribute("detalle", detalle);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/deuda/crear");
        }
        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam Double montoAPagar, @RequestParam String detalle, RedirectAttributes redirectAttributes) {
        try {
            deudaService.modificar(id, montoAPagar, detalle);
            redirectAttributes.addFlashAttribute("éxito", "La deuda se modificó correctamente.");
        }catch (MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/deudas");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            deudaService.eliminar(id);
            redirectAttributes.addFlashAttribute("éxito", "El gasto se dió de baja correctamente.");
        }catch (MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/deudas");
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            deudaService.habilitar(id);
            redirectAttributes.addFlashAttribute("éxito", "La deuda se dió de alta correctamente.");
        }catch (MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/deudas/deshabilitados");
    }

}

