package com.robson.desafiopicpay.services.chain;



import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.services.UsuarioService;
import com.robson.desafiopicpay.services.exceptions.DuplicateDataException;

// @Component
public class TratadorEmail extends TratadorCadastro {
    private UsuarioService service;

    public TratadorEmail(UsuarioService service) {
        this.service = service;
    }

    @Override
    public void tratarRequisicao(Usuario usuario){
        String email = usuario.getEmail();
        Usuario usuarioSalvo = service.findByEmail(email).usuario();
        if(usuarioSalvo != null){
            throw new DuplicateDataException(email);
        }
        super.tratarRequisicao(usuario);
        
    }
    
}
