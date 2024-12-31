package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.EstadoResponseDTO;
import com.dino.algafood.api.domain.entity.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoAssembler {

    @Autowired
    private ModelMapper mapper;

    public EstadoResponseDTO toDTO(Estado estado) {
        return mapper.map(estado, EstadoResponseDTO.class);
    }

    public List<EstadoResponseDTO> toCollectionDTO(List<Estado> estados) {
        return estados.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
