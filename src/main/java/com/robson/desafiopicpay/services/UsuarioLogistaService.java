package com.robson.desafiopicpay.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.dtos.UsuarioDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioLogistaRequestDTO;
import com.robson.desafiopicpay.entities.usuarios.UsuarioLogista;
import com.robson.desafiopicpay.repositories.UsuarioLogistaRepository;
import com.robson.desafiopicpay.services.chain.TratadorCadastro;
import com.robson.desafiopicpay.services.chain.TratadorCnpj;
import com.robson.desafiopicpay.services.chain.TratadorEmail;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;

@Service
public class UsuarioLogistaService {
    private UsuarioLogistaRepository repository;
    private UsuarioService usuarioService;

    public UsuarioLogistaService(UsuarioLogistaRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public List<UsuarioDTO> findAll(){
        List<UsuarioLogista> listaDeUsuarios = repository.findAll();
        List<UsuarioDTO> response = new ArrayList<>();
        for (UsuarioLogista usuario : listaDeUsuarios) {
            UsuarioDTO dto = new UsuarioDTO(usuario);
            response.add(dto);
        }
        return response;
    }

    public UsuarioDTO findById(Long id){
        Optional<UsuarioLogista> usuario = repository.findById(id);
        if(usuario.isPresent()){
            return new UsuarioDTO(usuario.get());
        }
        throw new UserNotFoundException(id);
    }

    public UsuarioDTO insert(UsuarioLogistaRequestDTO usuario){
        UsuarioLogista usuarioLogista = new UsuarioLogista(usuario);
        TratadorCadastro tratadorEmail = new TratadorEmail(usuarioService);
        TratadorCadastro tratador = new TratadorCnpj(repository);
        tratadorEmail.setProximoTratador(tratador);
        tratadorEmail.tratarRequisicao(usuarioLogista);
        usuarioLogista = repository.save(usuarioLogista);
        return new UsuarioDTO(usuarioLogista);
    }
}
