package com.dino.algafood.api.api.disassembler;

import com.dino.algafood.api.api.model.input.CidadeRequestDTO;
import com.dino.algafood.api.domain.model.entity.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Cidade toDomain(CidadeRequestDTO dto){
        return mapper.map(dto, Cidade.class);
    }

    public void copyToDomain(CidadeRequestDTO dto, Cidade cidade){
        mapper.map(dto, cidade);
    }
}
