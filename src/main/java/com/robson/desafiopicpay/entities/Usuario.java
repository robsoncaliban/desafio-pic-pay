package com.robson.desafiopicpay.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


import com.robson.desafiopicpay.dtos.request.UsuarioRequestDTO;
import com.robson.desafiopicpay.entities.enums.TipoUsuario;
import com.robson.desafiopicpay.validation.constraint.CpfCnpj;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    @NotBlank
    @Email(message = "Formato de email inválido")
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    private String nomeCompleto;

    @OneToOne(cascade = CascadeType.ALL)
    private Conta conta;

    @CpfCnpj
    private String cpfOuCnpj;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "usuario_role",
        joinColumns = {@JoinColumn(name="usuario_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    public Usuario(){
        this.authorities = new HashSet<>();
    }
    public Usuario(UsuarioRequestDTO usuarioDto) {
        this.email = usuarioDto.email();
        this.senha = usuarioDto.senha();
        this.nomeCompleto = usuarioDto.nomeCompleto();
        this.cpfOuCnpj = usuarioDto.cpfCnpj();
        this.conta = new Conta(this, new BigDecimal(usuarioDto.saldo()));
        this.authorities = new HashSet<>();
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }
    public void addAuthority(Role authority) {
        this.authorities.add(authority);
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
