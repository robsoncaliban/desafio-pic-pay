package com.robson.desafiopicpay.entities.command;

import com.robson.desafiopicpay.entities.Transacao;

public abstract class TransacaoComando {
    protected Transacao transacao;

    protected TransacaoComando(Transacao transacao) {
        this.transacao = transacao;
    }
    public abstract Transacao execute();
}
