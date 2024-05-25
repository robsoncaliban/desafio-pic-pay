package com.robson.desafiopicpay.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService{
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    public void enviarEmailTexto(String emailDestino, String assunto, String mensagemHtml) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(remetente);
        mimeMessageHelper.setTo(emailDestino);
        mimeMessageHelper.setSubject(assunto);
        mimeMessageHelper.setText(mensagemHtml, true);
        javaMailSender.send(message);
    }
}
