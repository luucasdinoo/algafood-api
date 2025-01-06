package com.dino.algafood.api.api.disassembler;

import com.dino.algafood.api.api.model.input.CozinhaResquestDTO;
import com.dino.algafood.api.domain.model.entity.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Cozinha toDomain(CozinhaResquestDTO dto){
        return mapper.map(dto, Cozinha.class);
    }

    public void copyToDomain(CozinhaResquestDTO dto, Cozinha cozinha){
        mapper.map(dto, cozinha);
    }
}
