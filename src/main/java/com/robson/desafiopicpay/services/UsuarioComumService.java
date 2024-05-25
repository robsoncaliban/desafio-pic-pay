package com.robson.desafiopicpay.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.dtos.UsuarioDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioComumRequestDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.command.UsuarioComum;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.repositories.UsuarioComumRepository;
import com.robson.desafiopicpay.services.chain.TratadorCadastro;
import com.robson.desafiopicpay.services.chain.TratadorCpf;
import com.robson.desafiopicpay.services.chain.TratadorEmail;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;

@Service
public class UsuarioComumService {
    private UsuarioComumRepository repository;
    private UsuarioService usuarioService;
    

    public UsuarioComumService(UsuarioComumRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public List<UsuarioDTO> findAll(){
        List<UsuarioComum> listaDeUsuarios = repository.findAll();
        List<UsuarioDTO> response = new ArrayList<>();
        for (UsuarioComum usuario : listaDeUsuarios) {
            UsuarioDTO dto = new UsuarioDTO(usuario);
            response.add(dto);
        }
        return response;
    }

    public UsuarioDTO findById(Long id){
        Optional<UsuarioComum> usuario = repository.findById(id);
        if(usuario.isPresent()){
            return new UsuarioDTO(usuario.get());
        }
        throw new UserNotFoundException(id);
    }

    public List<Transacao> findTransacoesEnviadasByUserId(Long id){
        Usuario usuario = findById(id).usuario();
        return ((UsuarioComum) usuario).getHistoricoDeTransacoesEnviadas();
    }

    public UsuarioDTO insert(UsuarioComumRequestDTO usuario){
        UsuarioComum usuarioComum = new UsuarioComum(usuario);
        TratadorCadastro tratadorEmail = new TratadorEmail(usuarioService);
        TratadorCadastro tratador = new TratadorCpf(repository);
        tratadorEmail.setProximoTratador(tratador);
        tratadorEmail.tratarRequisicao(usuarioComum);
        usuarioComum = repository.save(usuarioComum);
        return new UsuarioDTO(usuarioComum);
    }
}
