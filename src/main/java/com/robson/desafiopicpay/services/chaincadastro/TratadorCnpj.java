package com.robson.desafiopicpay.services.chaincadastro;


import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.entities.usuarios.UsuarioLogista;
import com.robson.desafiopicpay.repositories.UsuarioLogistaRepository;
import com.robson.desafiopicpay.services.UsuarioLogistaService;
import com.robson.desafiopicpay.services.exceptions.DuplicateDataException;

// @Component
public class TratadorCnpj extends TratadorCadastro{

    private UsuarioLogistaService service;

    public TratadorCnpj(UsuarioLogistaService service) {
        this.service = service;
    }

    @Override
    public void tratarRequisicao(Usuario usuario){
        String cnpj = ((UsuarioLogista) usuario).getCNPJ();
        Usuario usuarioSalvo = service.findByCnpj(cnpj);
        if(usuarioSalvo != null){
           throw new DuplicateDataException(cnpj);
        }
        super.tratarRequisicao(usuario);
    }
    
}
