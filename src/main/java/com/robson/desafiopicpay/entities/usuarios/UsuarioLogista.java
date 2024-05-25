package com.robson.desafiopicpay.entities.usuarios;

import org.hibernate.validator.constraints.br.CNPJ;

import com.robson.desafiopicpay.dtos.request.UsuarioLogistaRequestDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.services.exceptions.TransactionForbiddenException;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity(name = "usuario_logista")
@DiscriminatorValue("L")
public class UsuarioLogista extends Usuario {

    @NotBlank
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;
    
    
    public UsuarioLogista() {
    }

    public UsuarioLogista(UsuarioLogistaRequestDTO dto) {
        super(dto.email(), dto.senha(), dto.nomeCompleto(), dto.saldo());
        this.cnpj = dto.cnpj();
    }


    @Override
    public Transacao efetuarTransacao(double valor, Usuario usuario) {
        throw new TransactionForbiddenException(getId());
    }

    @Override
    public Transacao cancelarUltimaTransacao() {
        throw new TransactionForbiddenException(getId());
    }

    public String getCNPJ() {
        return cnpj;
    }

    public void setCNPJ(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UsuarioLogista other = (UsuarioLogista) obj;
        if (cnpj == null) {
            if (other.cnpj != null)
                return false;
        } else if (!cnpj.equals(other.cnpj))
            return false;
        return true;
    }



 





    
}
