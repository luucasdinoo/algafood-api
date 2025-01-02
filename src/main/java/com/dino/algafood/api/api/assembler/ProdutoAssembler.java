package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.ProdutoResponseDTO;
import com.dino.algafood.api.domain.entity.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoAssembler {

    @Autowired
    private ModelMapper mapper;

    public ProdutoResponseDTO toDTO(Produto produto) {
        return mapper.map(produto, ProdutoResponseDTO.class);
    }

    public List<ProdutoResponseDTO> toCollectionDTO(List<Produto> produtos) {
        return produtos.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
