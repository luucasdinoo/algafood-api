package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.CozinhaResponseDTO;
import com.dino.algafood.api.domain.entity.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaAssembler {

    @Autowired
    private ModelMapper mapper;

    public CozinhaResponseDTO toDTO(Cozinha cozinha) {
        return mapper.map(cozinha, CozinhaResponseDTO.class);
    }

    public List<CozinhaResponseDTO> toCollectionDTO(List<Cozinha> cozinhas) {
        return cozinhas.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
