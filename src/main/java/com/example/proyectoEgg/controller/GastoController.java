package com.example.proyectoEgg.controller;

import com.example.proyectoEgg.entity.Categoria;
import com.example.proyectoEgg.entity.Gasto;
import com.example.proyectoEgg.exception.MiException;
import com.example.proyectoEgg.service.CategoriaService;
import com.example.proyectoEgg.service.GastoService;
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
@RequestMapping("/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping()
    public ModelAndView mostrarGastos(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("gasto-lista");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

       if(flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));

        }
        mav.addObject("accion", "eliminar");
        mav.addObject("gastos", gastoService.buscarHabilitados());
        mav.addObject("titulo", "Lista de Gastos");

        return mav;




    }

    @GetMapping("/deshabilitados")
    public ModelAndView gastosDeshabilitados(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("gasto-lista");
         Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));

        }

        mav.addObject("gastos", gastoService.BuscarDeshabilitados());
        mav.addObject("accion", "habilitar");
        mav.addObject("titulo", "Lista de gastos deshabilitados");

        return mav;


    }

    @GetMapping("/crear")
    public ModelAndView crearGasto(){
        ModelAndView mav = new ModelAndView("gasto-formulario");

        mav.addObject("gasto", new Gasto());
        mav.addObject("categorias", categoriaService.buscarHabilitados());
        mav.addObject("titulo", "Crear Gasto");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarGasto(@PathVariable Integer id){
            ModelAndView mav = new ModelAndView("gasto-formulario");
        try {
            Gasto gasto = gastoService.buscarPorId(id);
            mav.addObject("gasto", gasto);
        } catch(MiException e) {
            mav.addObject("error", e.getMessage());
        }

            mav.addObject("titulo", "Editar Gasto");
            mav.addObject("accion", "modificar");
            return mav;

        }

    @PostMapping("/guardar")
    public RedirectView guardarGasto(@RequestParam Categoria categoria, @RequestParam Double montoPagado, @RequestParam String detalle, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/gastos");
         try {
             gastoService.crear(categoria, montoPagado, detalle);
             redirectAttributes.addFlashAttribute("exito", "El gasto se registró correctatamente.");

         }catch (MiException e){
             redirectAttributes.addFlashAttribute("categoria", categoria);
             redirectAttributes.addFlashAttribute("montoPagado", montoPagado);
             redirectAttributes.addFlashAttribute("detalle", detalle);
             redirectAttributes.addFlashAttribute("error", e.getMessage());
             redirectView.setUrl("/gastos/crear");


         }
        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificarGasto(@RequestParam Integer id, @RequestParam Double montoPagado, @RequestParam String detalle, RedirectAttributes redirectAttributes){
        try {
            gastoService.modificar(id, montoPagado, detalle);
            redirectAttributes.addFlashAttribute("exito", "El gasto se modificó correctamente.");
        }catch (MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/gastos");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarGasto(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            gastoService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "El gasto se dió de baja correctamente.");
        }catch (MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/gastos");
    }


    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            gastoService.habilitar(id);
            redirectAttributes.addFlashAttribute("exito", "El gasto se dió de alta correctamente.");
        }catch (MiException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

          return new RedirectView("/gastos/deshabilitados");
    }


    }



