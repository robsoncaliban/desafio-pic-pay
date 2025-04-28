package com.robson.desafiopicpay.services;


import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.dtos.request.UsuarioRequestDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioUpdateRequestDTO;
import com.robson.desafiopicpay.entities.Role;
import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.entities.enums.RoleName;
import com.robson.desafiopicpay.repositories.RoleRepository;
import com.robson.desafiopicpay.repositories.UsuarioRepository;
import com.robson.desafiopicpay.services.chain.TratadorCadastro;
import com.robson.desafiopicpay.services.chain.TratadorCpfCnpj;
import com.robson.desafiopicpay.services.chain.TratadorEmail;
import com.robson.desafiopicpay.services.exceptions.RoleNotFoundException;
import com.robson.desafiopicpay.services.utils.BeanUtilsIgnoreNull;

import jakarta.transaction.Transactional;

@Service
public class AuthenticationService {

    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(UsuarioService usuarioService, UsuarioRepository usuarioRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public Usuario insert(UsuarioRequestDTO usuario) {
        Usuario novoUsuario = new Usuario(usuario);
        TratadorCadastro tratadorEmail = new TratadorEmail(usuarioService);
        TratadorCadastro tratador = new TratadorCpfCnpj(usuarioService);
        tratadorEmail.setProximoTratador(tratador);
        tratadorEmail.tratarRequisicao(novoUsuario);
        atribuirRolePorTipo(novoUsuario);
        novoUsuario.setSenha(passwordEncoder.encode(usuario.senha()));
        novoUsuario = usuarioRepository.save(novoUsuario);
        return novoUsuario;
    }
    private void atribuirRolePorTipo(Usuario usuario) {
        Role role;
        switch (usuario.getTipo()) {
            case USUARIO_COMUM:
                role = buscarOuCriarRole(RoleName.ROLE_USUARIO_COMUM);
                break;
            case USUARIO_LOGISTA:
                role = buscarOuCriarRole(RoleName.ROLE_USUARIO_LOGISTA);
                break;
            default:
                throw new RoleNotFoundException();
        }

        usuario.addAuthority(role);
    }
    private Role buscarOuCriarRole(RoleName authority) {
        Optional<Role> roles = roleRepository.findByAuthority(authority);
        if (roles.isPresent()) {
            return roles.get();
        }
        return new Role(authority);
    }

    public Usuario updateById(Long id, UsuarioUpdateRequestDTO usuarioUpdate){
        Usuario usuario = usuarioService.findById(id);
        String senhaCriptografada = (usuarioUpdate.senha() != null) ? passwordEncoder.encode(usuarioUpdate.senha()) : null;
        UsuarioUpdateRequestDTO update = new UsuarioUpdateRequestDTO(usuarioUpdate.nomeCompleto(), senhaCriptografada);
        BeanUtilsIgnoreNull.copyProperties(update, usuario);
        return usuarioRepository.save(usuario);
    }
}
