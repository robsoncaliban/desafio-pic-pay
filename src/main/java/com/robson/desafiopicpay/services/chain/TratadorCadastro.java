package com.robson.desafiopicpay.services.chain;
import com.robson.desafiopicpay.entities.usuarios.Usuario;

public abstract class TratadorCadastro {
    protected TratadorCadastro proximoTratador;

    public void tratarRequisicao(Usuario usuario){
        if(proximoTratador != null){
            proximoTratador.tratarRequisicao(usuario);
        }
    } 

    public TratadorCadastro getProximoTratador() {
        return proximoTratador;
    }

    public void setProximoTratador(TratadorCadastro proximoTratador) {
        this.proximoTratador = proximoTratador;
    }
}
