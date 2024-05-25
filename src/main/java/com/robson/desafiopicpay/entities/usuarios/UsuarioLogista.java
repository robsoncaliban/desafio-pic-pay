package com.robson.desafiopicpay.entities.usuarios;

import org.hibernate.validator.constraints.br.CNPJ;

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

    public UsuarioLogista(@Email(message = "email inválido") String email, String senha, String nomeCompleto,
            @CNPJ(message = "CNPJ inválido") String cnpj) {
        super(email, senha, nomeCompleto);
        this.cnpj = cnpj;
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
