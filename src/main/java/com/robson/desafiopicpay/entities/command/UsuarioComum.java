package com.robson.desafiopicpay.entities.command;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robson.desafiopicpay.dtos.request.UsuarioComumRequestDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.services.exceptions.TransactionNotCompletedException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity(name = "usuario_comum")
@DiscriminatorValue("C")
public class UsuarioComum extends Usuario {
    

    @OneToMany(mappedBy = "origem", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Transacao> historicoDeTransacoesEnviadas = new ArrayList<>();

    @CPF(message = "Formato de CPF inválido")
    @NotBlank
    private String cpf;
    

    public UsuarioComum() {
    }
    public UsuarioComum(UsuarioComumRequestDTO dto) {
        super(dto.email(), dto.senha(), dto.nomeCompleto(), dto.saldo());
        this.cpf = dto.cpf();
    }

    @Override
    public Transacao efetuarTransacao(double valor, Usuario detinatario){
        if(getSaldo() < valor) throw new TransactionNotCompletedException("Saldo insuficiente. id " + getId());
        Transacao transacao = new Transacao(valor, this, detinatario);
        transacao = executarComandoTransacao(new EfetuarTransacao(transacao));
        return transacao;
    }

    @Override
    public Transacao cancelarUltimaTransacao() {
        int tamanhoDaLista = getHistoricoDeTransacoesEnviadas().size();
        Transacao transacao = getHistoricoDeTransacoesEnviadas().get(tamanhoDaLista-1);
        transacao = executarComandoTransacao(new CancelarTransacao(transacao));
        return transacao;
    }

    public Transacao executarComandoTransacao(TransacaoComando comando) {
        return comando.execute();
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    public List<Transacao> getHistoricoDeTransacoesEnviadas() {
        return historicoDeTransacoesEnviadas;
    }
    public void addHistoricoDeTransacoesEnviadas(Transacao transacao) {
        this.historicoDeTransacoesEnviadas.add(transacao);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
        UsuarioComum other = (UsuarioComum) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        return true;
    }
    


    
}
