package com.robson.desafiopicpay.services.chain;



import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.services.UsuarioService;
import com.robson.desafiopicpay.services.exceptions.DuplicateDataException;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;

// @Component
public class TratadorEmail extends TratadorCadastro {
    private UsuarioService service;

    public TratadorEmail(UsuarioService service) {
        this.service = service;
    }

    @Override
    public void tratarRequisicao(Usuario usuario){
        String email = usuario.getEmail();
        try {
            service.findByEmail(email);
            throw new DuplicateDataException(email);
        } catch (UserNotFoundException e) {
            super.tratarRequisicao(usuario);
        }
        
    }
    
}
