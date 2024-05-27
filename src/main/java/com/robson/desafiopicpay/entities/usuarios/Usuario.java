package com.robson.desafiopicpay.entities.usuarios;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robson.desafiopicpay.entities.Transacao;


import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DISC", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("U")
public abstract class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Email(message = "Formato de email inválido")
    private String email;
    @NotBlank
    @Size(min = 6, max = 6, message = "Deve ter 6 caracteres")
    private String senha;
    @NotBlank
    @Size(min = 5, message = "Deve ter no minimo 5 caracteres")
    @Pattern(regexp = "^(?!.*\\d)(?!.*[!@#$%^&*()_+={}\\[\\]|\\\\:;\"'<>,.?/~`])[A-Za-zÀ-ÿ]+ [A-Za-zÀ-ÿ ']{1,}$")
    private String nomeCompleto;
    @PositiveOrZero(message = "Saldo invalido")
    private double saldo;

    @OneToMany(mappedBy = "destino")
    @JsonIgnore
    private List<Transacao> historicoDeTransacaosRecebidas = new ArrayList<>();


    protected Usuario(){
    }
    protected Usuario(@Email(message = "email inválido") String email, String senha, String nomeCompleto, double saldo) {
        this.email = email;
        this.senha = senha;
        this.nomeCompleto = nomeCompleto;
        this.saldo = saldo;
    }

    public abstract Transacao efetuarTransacao(double valor, Usuario usuario);
    public abstract Transacao cancelarUltimaTransacao();

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


    public List<Transacao> getHistoricoDeTransacaosRecebidas() {
        return historicoDeTransacaosRecebidas;
    }
    public void addHistoricoDeTransacaosRecebidas(Transacao transacao) {
        this.historicoDeTransacaosRecebidas.add(transacao);
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((email == null) ? 0 : email.hashCode());
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
        Usuario other = (Usuario) obj;
        if (id != other.id)
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }

   

    
}
