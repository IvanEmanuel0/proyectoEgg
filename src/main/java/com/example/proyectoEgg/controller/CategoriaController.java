package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.service.CategoriaService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping()
    public ModelAndView mostrarCategorias(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("categoria-lista");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("accion", "eliminar");
        mav.addObject("categorias", categoriaService.buscarHabilitados());
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
    public ModelAndView crear(){
        ModelAndView mav = new ModelAndView("categoria-formulario");
        mav.addObject("categoria" , new Categoria());
        mav.addObject("titulo", "Crear Categoria");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")
    public ModelAndView editarCategoria(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("categoria-formulario");
        Categoria categoria = categoriaService.buscarPorId(id);
        mav.addObject("categoria", categoria);
        mav.addObject("titulo", "Editar categoria");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")
    public RedirectView guardarCategoria(@RequestParam String nombre, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/categorias");
        categoriaService.crear(nombre);
        return redirectView;
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")

    public RedirectView modificarCategoria(@RequestParam Integer id,@RequestParam String nombre, RedirectAttributes redirectAttributes){
        categoriaService.modificar(id, nombre);
        return new RedirectView("/categorias");
    }
    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")
    public RedirectView eliminarCategoria(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        categoriaService.eliminar(id);
        return new RedirectView("/categorias");
    }


    @PostMapping("/habilitar/{id}")
    @PreAuthorize("hasAnyRole('USERPRO', 'ADMIN')")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        categoriaService.habilitar(id);
        return new RedirectView("/categorias");
    }

}
