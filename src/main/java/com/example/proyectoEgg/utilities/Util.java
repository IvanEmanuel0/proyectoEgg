package com.example.proyectoEgg.utilities;

import com.example.proyectoEgg.exception.MiException;

public class Util {
    public static void noEsVacio(String respuesta) throws MiException{
        if(respuesta.trim().isEmpty() || respuesta == null) throw new MiException("El campo no debe estar vacio");
    }

    public static void esNumero(String numero) throws MiException{
        noEsVacio(numero);
        if(!numero.matches("[+-]?([0-9]*[.])?[0-9]+$")) throw new MiException("Solo se de deben ingresar números");

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
        if(!clave.matches("(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$")) throw new MiException("La clave debe tener entre 8 y 16 carácteres, al menos un dígito,  al menos una minúscula, y al menos una mayúscula.");
    }

    public static void validarUsuario(String usuario) throws MiException{
        esNumero(usuario);
        if(!usuario.matches("^ [A-Za-z] \\\\ w {5,29} $")) throw new MiException("La clave debe tener entre 6 y 15 carácteres alfanúmericos. La primer letra debe ser alfábetica.");
    }


}
