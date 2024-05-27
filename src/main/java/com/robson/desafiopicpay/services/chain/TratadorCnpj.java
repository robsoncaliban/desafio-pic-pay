package com.robson.desafiopicpay.services.chain;


import com.robson.desafiopicpay.entities.command.UsuarioComum;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.entities.usuarios.UsuarioLogista;
import com.robson.desafiopicpay.repositories.UsuarioLogistaRepository;
import com.robson.desafiopicpay.services.UsuarioLogistaService;
import com.robson.desafiopicpay.services.exceptions.DuplicateDataException;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;

// @Component
public class TratadorCnpj extends TratadorCadastro{

    private UsuarioLogistaService service;

    public TratadorCnpj(UsuarioLogistaService service) {
        this.service = service;
    }

    @Override
    public void tratarRequisicao(Usuario usuario){
        String cnpj = ((UsuarioLogista) usuario).getCNPJ();
        try {
            service.findByCnpj(cnpj);
            throw new DuplicateDataException(cnpj);
        } catch (UserNotFoundException e) {
            super.tratarRequisicao(usuario);
        }
    }
    
}
