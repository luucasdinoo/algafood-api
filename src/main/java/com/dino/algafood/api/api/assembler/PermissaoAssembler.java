package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.PermissaoResponseDTO;
import com.dino.algafood.api.domain.entity.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoAssembler {

    @Autowired
    private ModelMapper mapper;

    public PermissaoResponseDTO toDTO(Permissao permissao) {
        return mapper.map(permissao, PermissaoResponseDTO.class);
    }

    public List<PermissaoResponseDTO> toCollectionDTO(List<Permissao> permissoes) {
        return permissoes.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
