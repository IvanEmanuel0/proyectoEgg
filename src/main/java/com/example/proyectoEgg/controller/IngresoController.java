package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Ingreso;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.service.CategoriaService;
import com.example.proyectoEgg.service.IngresoService;

import com.example.proyectoEgg.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ingresos")
public class IngresoController {

    @Autowired
    private IngresoService ingresoService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ModelAndView mostrarTodos(HttpServletRequest request, HttpSession session){
        ModelAndView mav = new ModelAndView("Lista-Ingreso");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        try {
            List<Categoria> categorias = categoriaService.buscarHabilitados(personaService.buscarPorCuenta((Integer)session.getAttribute("idSession")));
            mav.addObject("ingresos", ingresoService.calcularIngresosPorCategoria(categorias));//SEGUIR DE ACA BUSCAR LA CATEGORIA EN PARTICULAR QUE QUIERO MOSTRAR
        } catch (MiException e) {
            mav.addObject("error-ingreso", e.getMessage());
        }

        if (flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("accion", "eliminar");
        mav.addObject("titulo", "Lista de Ingresos");

        return mav;

    }

    @GetMapping("/deshabilitados")
    public ModelAndView mostrarDeshabilitados(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("ingreso-lista");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("ingresos", ingresoService.buscarDeshabilitados());
        mav.addObject("accion", "habilitar");
        mav.addObject("titulo", "Lista de Ingresos deshabilitados");
        return mav;

    }

    @GetMapping("/crear")
    public ModelAndView crearIngreso(HttpSession session){
        ModelAndView mav = new ModelAndView("ingreso-formulario");
        try {
            mav.addObject("categorias", categoriaService.buscarHabilitados(personaService.buscarPorCuenta((Integer)session.getAttribute("idSession"))));
        } catch (MiException e) {
            System.out.println("error");
        }
        mav.addObject("ingreso", new Ingreso());
        mav.addObject("titulo", "Crear Ingreso");
        mav.addObject("accion", "guardar");

        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarIngreso(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("ingreso-formulario");
        try{
            mav.addObject("ingreso", ingresoService.buscarPorId(id));
        }catch(MiException e){
            mav.addObject("error", e.getMessage());
        }

        mav.addObject("titulo", "Editar Ingreso");
        mav.addObject("accion", "modificar");
        return mav;
    }


    ////////////////////////////////////////POST////////////////////////////////////////

    @PostMapping("/guardar")
    public RedirectView guardarIngreso(@RequestParam Categoria categoria, @RequestParam Double montoIngresado, @RequestParam String detalle, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/ingresos");
        try{
            ingresoService.crear(categoria, montoIngresado, detalle);
            redirectAttributes.addFlashAttribute("exito", "El ingreso se registró correctamente.");
        }catch(MiException e){
            redirectAttributes.addFlashAttribute("categoria", categoria);
            redirectAttributes.addFlashAttribute("montoIngresado", montoIngresado);
            redirectAttributes.addFlashAttribute("detalle", detalle);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/ingresos/crear");
        }

            return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificarIngreso(@RequestParam Integer id, @RequestParam Double montoIngresado, @RequestParam String detalle, RedirectAttributes redirectAttributes){
        try{
            ingresoService.modificar(id, montoIngresado, detalle);
            redirectAttributes.addFlashAttribute("exito", "El ingreso se modificó correctamente.");
        }catch(MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/ingresos");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarIngreso(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try{
            ingresoService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "El ingreso se eliminó correctamente.");
        }catch(MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/ingresos");
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitarIngreso(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try{
            ingresoService.habilitar(id);
            redirectAttributes.addFlashAttribute("exito", "El ingreso se dió de alta correctamente.");
        }catch(MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/ingresos/deshabilitados");
    }















}
