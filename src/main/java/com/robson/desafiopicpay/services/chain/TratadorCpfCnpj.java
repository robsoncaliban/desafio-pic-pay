package com.robson.desafiopicpay.services.chain;


import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.entities.enums.TipoUsuario;
import com.robson.desafiopicpay.services.UsuarioService;
import com.robson.desafiopicpay.services.exceptions.DuplicateDataException;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;

public class TratadorCpfCnpj extends TratadorCadastro{


    public TratadorCpfCnpj(UsuarioService usuarioService) {
        super(usuarioService);
    }

    @Override
    public void tratarRequisicao(Usuario usuario){
        String cpfCnpj = usuario.getCpfOuCnpj();
        try {
            getUsuarioService().findByCpfCnpj(cpfCnpj);
            throw new DuplicateDataException(cpfCnpj);
        } catch (UserNotFoundException e) {
            addTipo(usuario);
            super.tratarRequisicao(usuario);
        }
    }

    private void addTipo(Usuario usuario){
        if(usuario.getCpfOuCnpj().length() == 11){
            usuario.setTipo(TipoUsuario.USUARIO_COMUM);
            return;
        }
        usuario.setTipo(TipoUsuario.USUARIO_LOGISTA);
    }
    
}
