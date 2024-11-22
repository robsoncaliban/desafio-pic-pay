package com.robson.desafiopicpay.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.dtos.request.EmailRequestDTO;

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

    @Async
    public CompletableFuture<Void> enviarEmailTexto(EmailRequestDTO email){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(remetente);
            mimeMessageHelper.setTo(email.emailDestino());
            mimeMessageHelper.setSubject(email.assunto());
            mimeMessageHelper.setText(email.mensagemHtml(), true);
            javaMailSender.send(message);    
            return CompletableFuture.completedFuture(null);
        } catch (MessagingException e) {
            return CompletableFuture.failedFuture(e);
        }   
    }
}
