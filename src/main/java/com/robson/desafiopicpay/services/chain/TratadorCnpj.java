package com.robson.desafiopicpay.services.chain;


import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.entities.usuarios.UsuarioLogista;
import com.robson.desafiopicpay.repositories.UsuarioLogistaRepository;
import com.robson.desafiopicpay.services.exceptions.DuplicateDataException;

// @Component
public class TratadorCnpj extends TratadorCadastro{

    private UsuarioLogistaRepository repository;

    public TratadorCnpj(UsuarioLogistaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void tratarRequisicao(Usuario usuario){
        String cnpj = ((UsuarioLogista) usuario).getCNPJ();
        Usuario usuarioSalvo = repository.findByCnpj(cnpj);
        if(usuarioSalvo != null){
           throw new DuplicateDataException(cnpj);
        }
        super.tratarRequisicao(usuario);
    }
    
}
