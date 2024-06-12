package com.robson.desafiopicpay.services;


import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.dtos.request.TransacaoRequestDTO;
import com.robson.desafiopicpay.dtos.response.TransacaoInsertResponseDTO;
import com.robson.desafiopicpay.entities.Conta;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.enums.StatusTransacao;
import com.robson.desafiopicpay.repositories.TransacaoRepository;
import com.robson.desafiopicpay.services.exceptions.TransactionNotCompletedException;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;


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

    @Transactional
    public TransacaoInsertResponseDTO efetuarTransacao(TransacaoRequestDTO transacao){
        validarTransacao(transacao);
        Conta contaOrigem = usuarioService.findById(transacao.idOrigem()).getConta();
        Conta contaDestino = usuarioService.findById(transacao.idDestino()).getConta();
        
        Transacao transacaoEfetuada = contaOrigem.efetuarTransacao(new BigDecimal(transacao.valor()), contaDestino);
        try {
            enviarEmail(transacaoEfetuada);
        } catch (MessagingException e) {
            transacaoEfetuada.setStatus(StatusTransacao.EMAIL_PENDENTE);
        }finally{
            repository.save(transacaoEfetuada);
        }
        return new TransacaoInsertResponseDTO(transacaoEfetuada);  
    }

    private void validarTransacao(TransacaoRequestDTO transacao){
        BigDecimal valor = new BigDecimal(transacao.valor());
        Conta conta = usuarioService.findById(transacao.idOrigem()).getConta();
        if(valor.compareTo(BigDecimal.ZERO) <= 0) throw new TransactionNotCompletedException("Valor igual ou menor que zero");
        if(conta.getSaldo().compareTo(valor) <= 0) throw new TransactionNotCompletedException("Saldo insuficiente");
        if(transacao.idOrigem().equals(transacao.idDestino())) throw new TransactionNotCompletedException("Não é possivel transferir para sua própria conta");
        if(!conta.getDono().autenticar(transacao.senhaOrigem())) throw new TransactionNotCompletedException("Senha incorreta");
    }

    private void enviarEmail(Transacao transacao) throws MessagingException{
        Conta cDestino = transacao.getDestino();
        Conta cOrigem = transacao.getOrigem();
        String assunto = "Recebimento de Transação";
        String mensagemHtml = "Olá " + cDestino.getDono().getNomeCompleto() + ",<br><br>"
        + "Gostaríamos de confirmar que a seguinte transação foi recebida com sucesso:<br><br>"
        + "<b>Enviada por:</b>" + cOrigem.getDono().getNomeCompleto() + "<br>"
        + "<b>Valor:</b> " + transacao.getValor() + "<br>"
        + "<b>Saldo Atual:</b> " + cDestino.getSaldo() + "<br>";
        emailService.enviarEmailTexto(cDestino.getDono().getEmail(), assunto, mensagemHtml);  
    }
}
