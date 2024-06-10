package com.robson.desafiopicpay.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import com.robson.desafiopicpay.entities.enums.StatusTransacao;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name = "transacao")
@Table(name = "transacoes")
public class Transacao implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Temporal(TemporalType.TIMESTAMP)
    private Instant data;
    
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private StatusTransacao status;

    @ManyToOne
    @JoinColumn(name = "origem_id")
    private Conta origem;
    @ManyToOne
    @JoinColumn(name = "destino_id")
    private Conta destino;
    
    public Transacao() {
    }
    public Transacao(BigDecimal valor, Conta origem, Conta destino, StatusTransacao status) {
        this.data = Instant.now();
        this.valor = valor;
        this.origem = origem;
        this.destino = destino;
        this.status = status;
    }

    public long getId() {
        return id;
    }
    public Instant getData() {
        return data;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public Conta getOrigem() {
        return origem;
    }
    public void setOrigem(Conta origem) {
        this.origem = origem;
    }
    public Conta getDestino() {
        return destino;
    }
    public void setDestino(Conta destino) {
        this.destino = destino;
    }
    public StatusTransacao getStatus() {
        return status;
    }
    public void setStatus(StatusTransacao status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transacao other = (Transacao) obj;
        return id != other.id;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
