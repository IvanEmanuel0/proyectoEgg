package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Persona;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.service.CategoriaService;
import com.example.proyectoEgg.service.CuentaService;
import com.example.proyectoEgg.service.PersonaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private CuentaService cuentaService;

    @GetMapping()
    public ModelAndView mostrarCategorias(HttpServletRequest request, HttpSession session){
        ModelAndView mav = new ModelAndView("categoria-lista");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        try {
            Integer idCuenta = (Integer)session.getAttribute("idSession");
            Persona persona = personaService.buscarPorCuenta(idCuenta);
            List<Categoria> categorias = categoriaService.buscarHabilitados(persona);
            mav.addObject("cuenta", cuentaService.buscarPorId(idCuenta));
            mav.addObject("persona", persona);
            mav.addObject("categorias", categorias);

        } catch(MiException e){
            System.out.println("error");
        }

        if(flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("accion", "eliminar");

        mav.addObject("titulo", "Lista de categorias");
        return mav;
    }

    @GetMapping("/deshabilitados")
    public ModelAndView categoriasDesahilitados(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("categoria-lista");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("gastos", categoriaService.BuscarDeshabilitados());
        mav.addObject("accion", "habilitar");
        mav.addObject("titulo", "Lista de categorias deshabilitados");
        return mav;
    }

    @GetMapping("/crear")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")
    public ModelAndView crear(HttpSession session){
        ModelAndView mav = new ModelAndView("categoria-formulario");
        try {
            Integer idCuenta = (Integer)session.getAttribute("idSession");
            Persona persona = personaService.buscarPorCuenta(idCuenta);
            List<Categoria> categorias = categoriaService.buscarHabilitados(persona);
            mav.addObject("cuenta", cuentaService.buscarPorId(idCuenta));
            mav.addObject("persona", persona);
        } catch (MiException e) {
            System.out.println("error");
        }
        mav.addObject("categoria" , new Categoria());
        mav.addObject("titulo", "Crear Categoria");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")
    public ModelAndView editarCategoria(@PathVariable Integer id, HttpSession session){
        ModelAndView mav = new ModelAndView("categoria-formulario");
        try{
            Categoria categoria = categoriaService.buscarPorId(id);
            Integer idCuenta = (Integer)session.getAttribute("idSession");
            Persona persona = personaService.buscarPorCuenta(idCuenta);

            mav.addObject("cuenta", cuentaService.buscarPorId(idCuenta));
            mav.addObject("persona", persona);
            mav.addObject("categoria", categoria);
            mav.addObject("cuenta", cuentaService.buscarPorId(idCuenta));
        }catch(MiException e){
            mav.addObject("error", e.getMessage());
        }

        mav.addObject("titulo", "Editar categoria");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")
    public RedirectView guardarCategoria(@RequestParam String nombre, HttpSession session,  RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/categorias");
        try{

            categoriaService.crear(nombre, personaService.buscarPorCuenta((Integer)session.getAttribute("idSession")));
            redirectAttributes.addFlashAttribute("exito", "Categoria creada correctamente.");
        }catch(MiException e){
            redirectAttributes.addFlashAttribute("nombre", nombre);
        }

        return redirectView;
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")

    public RedirectView modificarCategoria(@RequestParam Integer id,@RequestParam String nombre, RedirectAttributes redirectAttributes){
        try{
            categoriaService.modificar(id, nombre);
            redirectAttributes.addFlashAttribute("exito", "La categoria se modificó correctamente.");
        }catch(MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/categorias");
    }
    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")
    public RedirectView eliminarCategoria(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try{
            categoriaService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "La categoria se elimino correctamente.");
        }catch(MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/categorias");
    }


    @PostMapping("/habilitar/{id}")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try{
            categoriaService.habilitar(id);
            redirectAttributes.addFlashAttribute("exito", "La categoria se dio de alta correctamente.");
        }catch(MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return new RedirectView("/categorias");
    }

}
