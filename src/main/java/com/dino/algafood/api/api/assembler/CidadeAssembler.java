package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.CidadeResponseDTO;
import com.dino.algafood.api.domain.model.entity.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeAssembler {

    @Autowired
    private ModelMapper mapper;

    public CidadeResponseDTO toDTO(Cidade cidade) {
        return mapper.map(cidade, CidadeResponseDTO.class);
    }

    public List<CidadeResponseDTO> toCollectionDTO(List<Cidade> cidades) {
        return cidades.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
