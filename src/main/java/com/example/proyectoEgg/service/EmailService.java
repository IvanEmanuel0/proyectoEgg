package com.example.proyectoEgg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    private static final String SUBJECT = "¡Bienvenido a Smart Wallet!";
    private static final String TEXT = "¡Gracias por registrarte en Smart Wallet!\n"
            + "\nYa puedes disfrutar todos los beneficios que Smart Wallet tiene para ti.\n\n"
            + "\nEste es un correo generado automáticamente. Por favor, no respondas este correo.";

    @Async
    public void enviar(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(SUBJECT);
        message.setText(TEXT);
        sender.send(message);
    }
    /*
    public void enviarThread(String to) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(SUBJECT);
            message.setText(TEXT);
            sender.send(message);
        }).start();
    }
    */
}
