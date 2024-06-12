package com.robson.desafiopicpay.services.chain;
import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.services.UsuarioService;

public abstract class TratadorCadastro {
    protected TratadorCadastro proximoTratador;
    protected UsuarioService usuarioService;


    protected TratadorCadastro(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void tratarRequisicao(Usuario usuario){
        if(proximoTratador != null){
            proximoTratador.tratarRequisicao(usuario);
        }
    } 

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public TratadorCadastro getProximoTratador() {
        return proximoTratador;
    }

    public void setProximoTratador(TratadorCadastro proximoTratador) {
        this.proximoTratador = proximoTratador;
    }
}
