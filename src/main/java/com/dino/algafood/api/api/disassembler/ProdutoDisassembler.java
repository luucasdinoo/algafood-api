package com.dino.algafood.api.api.disassembler;

import com.dino.algafood.api.api.model.input.ProdutoRequestDTO;
import com.dino.algafood.api.domain.entity.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Produto toDomain(ProdutoRequestDTO dto){
        return mapper.map(dto, Produto.class);
    }

    public void copyToDomain(ProdutoRequestDTO dto, Produto produto){
        mapper.map(dto, produto);
    }
}
