package com.example.proyectoEgg.service;

import com.example.proyectoEgg.exception.MiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FotoService {

    @Value("${my.property}")
    private String directory;

    public String copiar(MultipartFile archivo) throws MiException {
        try {
            String nombreFoto = archivo.getOriginalFilename();
            Path rutaFoto = Paths.get(directory, nombreFoto).toAbsolutePath();
            Files.copy(archivo.getInputStream(), rutaFoto, StandardCopyOption.REPLACE_EXISTING);
            return nombreFoto;
        } catch (IOException e) {
            throw new MiException("Error al guardar foto");
        }
    }
}
