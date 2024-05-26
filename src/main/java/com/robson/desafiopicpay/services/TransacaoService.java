package com.robson.desafiopicpay.services;


import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.dtos.request.TransacaoRequestDTO;
import com.robson.desafiopicpay.dtos.response.TransacaoInsertResponseDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.command.UsuarioComum;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.repositories.TransacaoRepository;
import com.robson.desafiopicpay.services.exceptions.TransactionNotCompletedException;

import jakarta.mail.MessagingException;


@Service
public class TransacaoService {
    private TransacaoRepository repository;
    private UsuarioService usuarioService;
    private EmailService emailService;

    public TransacaoService(TransacaoRepository repository, UsuarioService service, EmailService emailService) {
        this.repository = repository;
        this.usuarioService = service;
        this.emailService = emailService;
    }  

    public TransacaoInsertResponseDTO efetuarTransacao(TransacaoRequestDTO transacao){
        if(transacao.idOrigem().equals(transacao.idDestino())) throw new TransactionNotCompletedException("Não é possivel transferir para sua própria conta");
        if(transacao.valor() <= 0) throw new TransactionNotCompletedException("Valor igual ou menor que zero");

        Usuario usuarioOrigem = usuarioService.findById(transacao.idOrigem());
        if (!transacao.senhaOrigem().equals(usuarioOrigem.getSenha())) {
            throw new TransactionNotCompletedException("Senha incorreta");
        }
        Usuario usuarioDestino = usuarioService.findById(transacao.idDestino());
        
        Transacao transacaoEfetuada = usuarioOrigem.efetuarTransacao(transacao.valor(), usuarioDestino);
        ((UsuarioComum) usuarioOrigem).addHistoricoDeTransacoesEnviadas(transacaoEfetuada);
        usuarioDestino.addHistoricoDeTransacaosRecebidas(transacaoEfetuada);
        try {
            enviarEmail(transacaoEfetuada);
        } catch (MessagingException e) {
            usuarioOrigem.cancelarUltimaTransacao();
            throw new TransactionNotCompletedException("Envio para o email: " + usuarioDestino.getEmail()+ " falhou");
        }finally{
            repository.save(transacaoEfetuada);
        }
        return new TransacaoInsertResponseDTO(transacaoEfetuada);  
    }

    private void enviarEmail(Transacao transacao) throws MessagingException{
        Usuario uDestino = transacao.getDestino();
        Usuario uOrigem = transacao.getOrigem();
        String assunto = "Recebimento de Transação";
        String mensagemHtml = "Olá " + uDestino.getNomeCompleto() + ",<br><br>"
        + "Gostaríamos de confirmar que a seguinte transação foi recebida com sucesso:<br><br>"
        + "<b>Enviada por:</b>" + uOrigem.getNomeCompleto() + "<br>"
        + "<b>Valor:</b> " + transacao.getValor() + "<br>"
        + "<b>Saldo Atual:</b> " + uDestino.getSaldo() + "<br>";
        emailService.enviarEmailTexto(uDestino.getEmail(), assunto, mensagemHtml);  
    }
}
