package com.robson.desafiopicpay.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.dtos.UsuarioDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioUpdateRequestDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.repositories.UsuarioRepository;

import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;
import com.robson.desafiopicpay.services.utils.UsuarioMapper;


@Service
public class UsuarioService{
    private UsuarioRepository repository;
    private UsuarioMapper mapper;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper  = mapper;
    }

    public List<UsuarioDTO> findAll(){
        List<Usuario> listaDeUsuarios = repository.findAll();
        List<UsuarioDTO> response = new ArrayList<>();
        for (Usuario usuario : listaDeUsuarios) {
            UsuarioDTO dto = new UsuarioDTO(usuario);
            response.add(dto);
        }
        return response;
    }

    public UsuarioDTO findById(Long id){
        Optional<Usuario> usuario = repository.findById(id);
        if(usuario.isPresent()){
            return new UsuarioDTO(usuario.get());
        }
        throw new UserNotFoundException(id);
    }

    public List<Transacao> findTransacoesRecebidasByUserId(Long id){
        Usuario usuario = findById(id).usuario();
        return usuario.getHistoricoDeTransacaosRecebidas();
    }

    public UsuarioDTO findByEmail(String email) {
        return new UsuarioDTO(repository.findByEmail(email));
    }

    public void delete(Long id){
        Usuario usuario = findById(id).usuario();
        repository.delete(usuario);
    }

    public UsuarioDTO updateById(Long id, UsuarioUpdateRequestDTO usuarioUpdate){
        Usuario usuario = findById(id).usuario();
        mapper.updateUsuarioFromDto(usuarioUpdate, usuario);
        return new UsuarioDTO(repository.save(usuario));
    }

}
