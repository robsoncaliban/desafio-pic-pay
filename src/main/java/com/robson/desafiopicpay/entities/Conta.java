package com.robson.desafiopicpay.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.robson.desafiopicpay.entities.enums.StatusTransacao;
import com.robson.desafiopicpay.entities.enums.TipoUsuario;
import com.robson.desafiopicpay.services.exceptions.TransactionForbiddenException;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "conta")
    @JoinColumn(name = "dono_id")
    private Usuario dono;
    
    @OneToMany(mappedBy = "origem", fetch = FetchType.LAZY)
    private List<Transacao> transacoesEnviadas;

    @OneToMany(mappedBy = "destino", fetch = FetchType.LAZY)
    private List<Transacao> transacoesRecebidas;

    private BigDecimal saldo;

    public Conta() {
    }
    public Conta(Usuario dono, BigDecimal saldo) {
        this.dono = dono;
        setSaldo(saldo);
    }

    public Transacao efetuarTransacao(BigDecimal valor, Conta destino){
        if(dono.getTipo().equals(TipoUsuario.USUARIO_LOGISTA)) throw new TransactionForbiddenException(dono.getId());
        
        setSaldo(getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));
        Transacao transacaoConcluida = new Transacao(valor, this, destino, StatusTransacao.CONCLUIDA);
        addTransacoesEnviadas(transacaoConcluida);
        destino.addTransacoesRecebidas(transacaoConcluida);
        return transacaoConcluida;  
    }

    public void addTransacoesEnviadas(Transacao transacao) {
        transacoesEnviadas.add(transacao);
    }
    public void addTransacoesRecebidas(Transacao transacao) {
        transacoesRecebidas.add(transacao);
    }

    public List<Transacao> getTransacoesEnviadas() {
        return transacoesEnviadas;
    }
    public List<Transacao> getTransacoesRecebidas() {
        return transacoesRecebidas;
    }

    public Long getId() {
        return id;
    }
    public Usuario getDono() {
        return dono;
    }
    public void setDono(Usuario dono) {
        this.dono = dono;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

}
