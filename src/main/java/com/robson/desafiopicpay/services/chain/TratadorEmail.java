package com.robson.desafiopicpay.services.chain;



import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.services.UsuarioService;
import com.robson.desafiopicpay.services.exceptions.DuplicateDataException;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;

// @Component
public class TratadorEmail extends TratadorCadastro {

    public TratadorEmail(UsuarioService usuarioService) {
        super(usuarioService);
    }

    @Override
    public void tratarRequisicao(Usuario usuario){
        String email = usuario.getEmail();
        try {
            getUsuarioService().findByEmail(email);
            throw new DuplicateDataException(email);
        } catch (UserNotFoundException e) {
            super.tratarRequisicao(usuario);
        }
        
    }
    
}
