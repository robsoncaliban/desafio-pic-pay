package com.robson.desafiopicpay.entities;

import java.io.Serializable;
import java.time.Instant;

import com.robson.desafiopicpay.entities.command.UsuarioComum;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.services.enums.EstadoTransacao;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "transacao")
@Table(name = "transacoes")
public class Transacao implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private Instant data;
    private double valor;

    @Enumerated(EnumType.STRING)
    private EstadoTransacao estado;

    @ManyToOne
    @JoinColumn(name = "origem_id")
    private UsuarioComum origem;
    @ManyToOne
    @JoinColumn(name = "destino_id")
    private Usuario destino;
    
    public Transacao() {
    }
    public Transacao(double valor, UsuarioComum origem, Usuario destino) {
        this.data = Instant.now();
        this.valor = valor;
        this.origem = origem;
        this.destino = destino;
    }

    public long getId() {
        return id;
    }
    public Instant getData() {
        return data;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public UsuarioComum getOrigem() {
        return origem;
    }
    public void setOrigem(UsuarioComum origem) {
        this.origem = origem;
    }
    public Usuario getDestino() {
        return destino;
    }
    public void setDestino(Usuario destino) {
        this.destino = destino;
    }
    public EstadoTransacao getEstado() {
        return estado;
    }
    public void setEstado(EstadoTransacao estado) {
        this.estado = estado;
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
        if (id != other.id)
            return false;
        return true;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
