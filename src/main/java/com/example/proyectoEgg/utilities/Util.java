package com.example.proyectoEgg.utilities;

import com.example.proyectoEgg.exception.MiException;

public class Util {
    public static void noEsVacio(String respuesta) throws MiException{
        if(respuesta.trim().isEmpty() || respuesta == null) throw new MiException("El campo no debe estar vacio");
    }

    public static void esNumero(String numero) throws MiException{
        noEsVacio(numero);
        if(!numero.matches("[+-]?([0-9]*[.])?[0-9]+\n")) throw new MiException("Solo se de deben ingresar números");

    }

    /*public static void esNumeroPositivo(String numero) throws MiException{
        noEsVacio(numero);
        if(!numero.matches("^[0-9]+$")) throw new MiException("Solo se de deben ingresar números positivos");

    }*/

    public static void sonLetras(String palabra) throws MiException{
        noEsVacio(palabra);
        if(palabra.matches("^-?[0-9]+$")) throw new MiException("Solo se deben ingresar letras");
    }

    public static void validarClave(String clave) throws MiException{
        esNumero(clave);
        if(!clave.matches("^\\w{10,13}+$")) throw new MiException("La clave debe tener entre 8 y 10 dígitos");
    }

    public static void validarUsuario(String usuario) throws MiException{
        esNumero(usuario);
        if(!usuario.matches("^\\w{10,13}+$")) throw new MiException("La clave debe tener entre 10 y 13 dígitos");
    }


}
