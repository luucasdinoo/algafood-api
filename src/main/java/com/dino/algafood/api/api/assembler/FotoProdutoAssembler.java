package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.FotoProdutoResponseDTO;
import com.dino.algafood.api.domain.model.entity.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoAssembler {

    @Autowired
    private ModelMapper mapper;

    public FotoProdutoResponseDTO toDTO(FotoProduto fotoProduto) {
        return mapper.map(fotoProduto, FotoProdutoResponseDTO.class);
    }
}
