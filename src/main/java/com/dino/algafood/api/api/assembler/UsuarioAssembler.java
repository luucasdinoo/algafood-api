package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.UsuarioResponseDTO;
import com.dino.algafood.api.domain.model.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioAssembler {

    @Autowired
    private ModelMapper mapper;

    public UsuarioResponseDTO toDTO(Usuario usuario) {
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    public List<UsuarioResponseDTO> toCollectionDTO(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
