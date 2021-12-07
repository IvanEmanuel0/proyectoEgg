package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Gasto;
import com.example.proyectoEgg.entity.Persona;
import com.example.proyectoEgg.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/personas")
public class PersonaController {
    @Autowired
    private PersonaService personaService;

    @GetMapping
    public ModelAndView mostrarPersonas(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("????");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));

        }


        mav.addObject("accion", "eliminar");
        mav.addObject("personas", personaService.buscarHabilitados());
        mav.addObject("titulo", "Lista de Personas");

        return mav;
    }

    @GetMapping("/desahilitados")
    public ModelAndView personasDesahilitadas(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("???");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));

        }
        mav.addObject("personas", personaService.buscarDeshabilitados());
        mav.addObject("accion", "habilitar");
        mav.addObject("titulo", "Lista de gastos deshabilitados");

        return mav;

    }

    @GetMapping("/crear")
    public ModelAndView crearPersonas(){
        ModelAndView mav = new ModelAndView("????");
        mav.addObject("persona" , new Persona());
        mav.addObject("titulo", "Crear Persona");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarPersona(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("???");
        Persona persona = personaService.buscarPorId(id);
        mav.addObject("persona", persona);
        mav.addObject("titulo", "Editar persona");
        mav.addObject("accion", "modificar");
        return mav;

    }

    @PostMapping("/guardar")
    public RedirectView guardarPersona(@RequestParam String nombre, @RequestParam String apellido,@RequestParam  Double montoDisponible, @RequestParam String usuario, @RequestParam String clave,@RequestParam  List<Categoria> listaDeCategorias,@RequestParam  String imagen, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("???");

        personaService.crear(nombre, apellido, montoDisponible, usuario, clave, listaDeCategorias, imagen);
        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificarPersona(@RequestParam Integer id,@RequestParam String nombre,@RequestParam String apellido,@RequestParam Double montoDisponible, @RequestParam List<Categoria> listaDeCategorias, @RequestParam String imagen, RedirectAttributes redirectAttributes){
        personaService.modificar(id, nombre, apellido, montoDisponible, listaDeCategorias,imagen);
        return new RedirectView("???");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@RequestParam Integer id, RedirectAttributes redirectAttributes){
        personaService.eliminar(id);
        return new RedirectView("??");
    }


    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        personaService.habilitar(id);
        return new RedirectView("???");
    }


}




