package com.robson.desafiopicpay.services.chaincadastro;


import com.robson.desafiopicpay.entities.command.UsuarioComum;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.repositories.UsuarioComumRepository;
import com.robson.desafiopicpay.services.UsuarioComumService;
import com.robson.desafiopicpay.services.exceptions.DuplicateDataException;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;

// @Component
public class TratadorCpf extends TratadorCadastro {

    private UsuarioComumService service;

    public TratadorCpf(UsuarioComumService service) {
        this.service = service;
    }


    @Override
    public void tratarRequisicao(Usuario usuario) {
        String cpf = ((UsuarioComum) usuario).getCPF();
        try {
            service.findByCpf(cpf);
            throw new DuplicateDataException(cpf);
        } catch (UserNotFoundException e) {
            super.tratarRequisicao(usuario);
        }
    }
    
}
