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

    public static void validarTresNumeros(String clave) throws MiException{
        noEsVacio(clave);
        if(!clave.matches("^[0-9]{3,3}$")) throw  new MiException("La clave debe ser de 3 digitos");

    }

    public static void validarDosNumeros(String dato) throws MiException {
        noEsVacio(dato);
        if(!dato.matches("^[0-9]{2,2}$")) throw new MiException("Mes o año deben ser 2 digitos");
    }

    public static void validarNumeroTarjeta(String numeroTarjeta) throws MiException {
        noEsVacio(numeroTarjeta);
        if (!numeroTarjeta.matches("^[0-9]{16,16}")) throw new MiException("Numero de tarjeta invalido");
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
        noEsVacio(clave);
        if(!clave.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$")) throw new MiException("La clave debe tener entre 8 y 16 carácteres, al menos una letra, un número y un caracter especial.");
    }

    public static void validarUsuario(String usuario) throws MiException{
        noEsVacio(usuario);
        if(!usuario.matches("^(?!.*[-_]{2,})(?=^[^-_].*[^-_]$)[\\w\\s-]{5,12}$")) throw new MiException("El usuario debe tener entre 5 y 12 carácteres alfanúmericos. Solo puede contener guiones bajos y medios.");
    }

    public static void validarEmail(String correo) throws MiException{
        noEsVacio(correo);
        if(!correo.matches("^(.+)@(.+)$")) throw new MiException("Ingrese un email válido");
    }


}
