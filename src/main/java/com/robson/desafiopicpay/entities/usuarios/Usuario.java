package com.robson.desafiopicpay.entities.usuarios;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robson.desafiopicpay.entities.Transacao;

import jakarta.persistence.Basic;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DISC", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("U")
public abstract class Usuario implements Serializable{
    private static  final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Email(message = "email inválido")
    private String email;
    private String senha;
    private String nomeCompleto;

    @OneToMany(mappedBy = "origem")
    @JsonIgnore
    private ArrayList<Transacao> historicoDeTransacoes;

    protected Usuario(){
    }
    protected Usuario(@Email(message = "email inválido") String email, String senha, String nomeCompleto) {
        this.email = email;
        this.senha = senha;
        this.nomeCompleto = nomeCompleto;
        this.historicoDeTransacoes = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public ArrayList<Transacao> getHistoricoDeTransacoes() {
        return historicoDeTransacoes;
    }

    public void setHistoricoDeTransacoes(ArrayList<Transacao> historicoDeTransacoes) {
        this.historicoDeTransacoes = historicoDeTransacoes;
    }
}
