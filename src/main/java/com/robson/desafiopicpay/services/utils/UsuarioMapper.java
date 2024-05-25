package com.robson.desafiopicpay.services.utils;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.robson.desafiopicpay.dtos.request.UsuarioUpdateRequestDTO;
import com.robson.desafiopicpay.entities.usuarios.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUsuarioFromDto(UsuarioUpdateRequestDTO dto, @MappingTarget Usuario entidade);
}
