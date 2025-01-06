package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.GrupoResponseDTO;
import com.dino.algafood.api.domain.model.entity.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoAssembler {

    @Autowired
    private ModelMapper mapper;

    public GrupoResponseDTO toDTO(Grupo grupo) {
        return mapper.map(grupo, GrupoResponseDTO.class);
    }

    public List<GrupoResponseDTO> toCollectionDTO(List<Grupo> grupos){
        return grupos.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
