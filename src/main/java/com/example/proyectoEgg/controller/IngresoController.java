package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Ingreso;
import com.example.proyectoEgg.service.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ingresos")
public class IngresoController {

    @Autowired
    private IngresoService ingresoService;

    @GetMapping
    public ModelAndView mostrarTodos(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("ingreso-lista"); //COMPLETAR
        List<Ingreso> ingresos = ingresoService.buscarHabilitados();
        mav.addObject("ingresos", ingresos); //CHECK

        mav.addObject("accion", "eliminar");
        mav.addObject("titulo", "Lista de Ingresos");

        return mav;

/*        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("accion", "Eliminar");
        mav.addObject("titulo", "Lista de Ingresos");
        return mav;
*/
    }

    @GetMapping("/deshabilitados")
    public ModelAndView mostrarDeshabilitados(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("ingresos"); //COMPLETAR
        List<Ingreso> ingresos = ingresoService.buscarDeshabilitados();
        mav.addObject("ingresos", ingresos); //CHECK

        mav.addObject("accion", "eliminar");
        mav.addObject("titulo", "Lista de Ingresos deshabilitados");

        return mav;


        /*
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("COMPLETAR", ingresoService.buscarDeshabilitados()); //COMPLETAR
        mav.addObject("accion", "habilitar");
        mav.addObject("titulo", "Lista de Ingresos deshabilitados");
        return mav;

     */
    }

    @GetMapping("/crear")
    public ModelAndView crearIngreso(){
        ModelAndView mav = new ModelAndView("ingreso-formulario"); //COMPLETAR

        mav.addObject("ingreso", new Ingreso()); //CHECK
        mav.addObject("titulo", "Crear Ingreso");
        mav.addObject("accion", "guardar");

        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarIngreso(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("ingreso-formulario");//COMPLETAR

        mav.addObject("ingreso", ingresoService.buscarPorId(id));//CHECK
        mav.addObject("titulo", "Editar Ingreso");
        mav.addObject("accion", "modificar");

        return mav;
    }

    ////////////////////////////////////////POST////////////////////////////////////////

    @PostMapping("/guardar")
    public RedirectView guardarIngreso(@RequestParam Double montoIngresado, @RequestParam String detalle){
            ingresoService.crear(montoIngresado, detalle);
            return new RedirectView("/ingresos"); //COMPLETAR
    }

    @PostMapping("/modificar")
    public RedirectView modificarIngreso(@RequestParam Integer id, @RequestParam Double montoIngresado, @RequestParam String detalle){
        ingresoService.modificar(id, montoIngresado, detalle);
        return new RedirectView("/ingresos"); //COMPLETAR
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarIngreso(@PathVariable Integer id){
        ingresoService.eliminar(id);
        return new RedirectView("/ingresos"); //COMPLETAR
    }

    @PostMapping("/habilitar/{id}")
    public RedirectView habilitarIngreso(@PathVariable Integer id){
        ingresoService.habilitar(id);
        return new RedirectView("/ingresos/deshabilitados"); //COMPLETAR
    }















}
