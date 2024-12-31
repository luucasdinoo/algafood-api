package com.dino.algafood.api.api.disassembler;

import com.dino.algafood.api.api.model.input.UsuarioRequestDTO;
import com.dino.algafood.api.domain.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Usuario toDomain(UsuarioRequestDTO dto){
        return mapper.map(dto, Usuario.class);
    }

    public void copyToDomain(UsuarioRequestDTO dto, Usuario usuario){
        mapper.map(dto, usuario);
    }
}
