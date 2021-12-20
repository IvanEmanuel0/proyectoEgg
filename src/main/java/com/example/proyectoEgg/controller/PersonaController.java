package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Gasto;
import com.example.proyectoEgg.entity.Persona;
import com.example.proyectoEgg.entity.Rol;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.service.PersonaService;
import com.example.proyectoEgg.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @Autowired
    private RolService rolService;

    @GetMapping
    public ModelAndView mostrarPersonas(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("persona-lista");
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

    @GetMapping("/deshabilitados")
    public ModelAndView personasDesahilitadas(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("persona-lista");
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
        ModelAndView mav = new ModelAndView("auth-signup");
        //mav.addObject("persona" , new Persona());
        mav.addObject("roles", rolService.buscarTodos());
        mav.addObject("titulo", "Crear Persona");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarPersona(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("persona-formulario");
        try{
            mav.addObject("persona", personaService.buscarPorId(id));
        }catch(MiException e){
            mav.addObject("error", e.getMessage());
        }
        mav.addObject("titulo", "Editar persona");
        mav.addObject("accion", "modificar");
        return mav;

    }

    @PostMapping("/guardar")
    public RedirectView guardarPersona(@RequestParam MultipartFile foto, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String usuario, @RequestParam String clave, @RequestParam Rol rol, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/personas");
        try {
            personaService.crear(nombre, apellido, usuario, clave,rol, foto);
            redirectAttributes.addFlashAttribute("exito", "La persona se registró correctatamente.");

        }catch (MiException e){
            redirectAttributes.addFlashAttribute("nombre", nombre);
            redirectAttributes.addFlashAttribute("apellido", apellido);
            redirectAttributes.addFlashAttribute("rol", rol);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/personas/crear");
        }

        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificarPersona(@RequestParam MultipartFile foto, @RequestParam Integer id,@RequestParam String nombre,@RequestParam String apellido, RedirectAttributes redirectAttributes){
        try {
            personaService.modificar(id, nombre, apellido, foto);
            redirectAttributes.addFlashAttribute("exito", "La persona se modificó correctamente.");
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/personas");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try{
            personaService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "La persona se eliminó correctamente.");
        }catch(MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/personas");
    }


    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try{
            personaService.habilitar(id);
            redirectAttributes.addFlashAttribute("exito", "La persona se dió de alta correctamente.");
        }catch(MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/personas");
    }


}




