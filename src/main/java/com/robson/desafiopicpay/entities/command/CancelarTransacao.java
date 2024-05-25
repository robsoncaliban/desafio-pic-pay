package com.robson.desafiopicpay.entities.command;

import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.services.enums.EstadoTransacao;

public class CancelarTransacao extends TransacaoComando{

    public CancelarTransacao(Transacao transacao) {
        super(transacao);
    }

    @Override
    public Transacao execute(){
        transacao.getOrigem().setSaldo(transacao.getOrigem().getSaldo() + transacao.getValor());
        transacao.getDestino().setSaldo(transacao.getDestino().getSaldo() - transacao.getValor());
        transacao.setEstado(EstadoTransacao.CANCELADA);
        return transacao;
    }
    
}
