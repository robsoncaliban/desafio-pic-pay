package com.robson.desafiopicpay.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.robson.desafiopicpay.dtos.request.UsuarioRequestDTO;
import com.robson.desafiopicpay.entities.enums.TipoUsuario;
import com.robson.desafiopicpay.validation.constraint.CpfCnpj;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    @NotBlank
    @Email(message = "Formato de email inválido")
    private String email;
    @NotBlank
    @Size(min = 6, max = 6, message = "Deve ter 6 caracteres")
    private String senha;
    @NotBlank
    @Size(min = 5, message = "Deve ter no minimo 5 caracteres")
    @Pattern(regexp = "^(?!.*\\d)(?!.*[!@#$%^&*()_+={}\\[\\]|\\\\:;\"'<>,.?/~`])[A-Za-zÀ-ÿ]+ [A-Za-zÀ-ÿ ']+$")
    private String nomeCompleto;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Conta conta;

    @CpfCnpj
    private String cpfOuCnpj;


    public Usuario(){
    }
    public Usuario(UsuarioRequestDTO usuarioDto) {
        this.email = usuarioDto.email();
        this.senha = usuarioDto.senha();
        this.nomeCompleto = usuarioDto.nomeCompleto();
        this.cpfOuCnpj = usuarioDto.cpfCnpj();
        this.conta = new Conta(this, new BigDecimal(usuarioDto.saldo()));
    }

    public boolean autenticar(String senha){
        return this.senha.equals(senha);
    }

    public Conta getConta() {
        return conta;
    }
    public void setConta(Conta conta) {
        this.conta = conta;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public TipoUsuario getTipo() {
        return tipo;
    }
    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }
    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((cpfOuCnpj == null) ? 0 : cpfOuCnpj.hashCode());
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (cpfOuCnpj == null) {
            if (other.cpfOuCnpj != null)
                return false;
        } else if (!cpfOuCnpj.equals(other.cpfOuCnpj))
            return false;
        return true;
    }
}
