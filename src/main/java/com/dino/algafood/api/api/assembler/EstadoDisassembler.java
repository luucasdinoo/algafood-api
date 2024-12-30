package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.input.EstadoRequestDTO;
import com.dino.algafood.api.domain.entity.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Estado toDomain(EstadoRequestDTO dto){
        return mapper.map(dto, Estado.class);
    }

    public void copyToDomain(EstadoRequestDTO dto, Estado estado){
        mapper.map(dto, estado);
    }
}
