package com.robson.desafiopicpay.services.chain;


import com.robson.desafiopicpay.entities.command.UsuarioComum;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.repositories.UsuarioComumRepository;
import com.robson.desafiopicpay.services.exceptions.DuplicateDataException;

// @Component
public class TratadorCpf extends TratadorCadastro {

    private UsuarioComumRepository repository;

    public TratadorCpf(UsuarioComumRepository repository) {
        this.repository = repository;
    }

    @Override
    public void tratarRequisicao(Usuario usuario) {
        String cpf = ((UsuarioComum) usuario).getCPF();
        Usuario usuarioSalvo = repository.findByCpf(cpf);
        if(usuarioSalvo != null){
           throw new DuplicateDataException(cpf);
        }
        super.tratarRequisicao(usuario);
    }
    
}
