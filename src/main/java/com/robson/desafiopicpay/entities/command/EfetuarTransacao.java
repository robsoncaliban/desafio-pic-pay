package com.robson.desafiopicpay.entities.command;

import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.services.enums.EstadoTransacao;

public class EfetuarTransacao extends TransacaoComando {

    public EfetuarTransacao(Transacao transacao) {
        super(transacao);
    }

    @Override
    public Transacao execute() {
        UsuarioComum uOrigem = transacao.getOrigem();
        Usuario uDestino = transacao.getDestino();
        
        uOrigem.setSaldo(uOrigem.getSaldo() - transacao.getValor());
        uDestino.setSaldo(uDestino.getSaldo() + transacao.getValor());

        transacao.setOrigem(uOrigem);
        transacao.setDestino(uDestino);
        transacao.setEstado(EstadoTransacao.CONCLUIDA);
        return transacao;
    }
    
}
