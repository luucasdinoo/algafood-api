package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.input.RestauranteRequestDTO;
import com.dino.algafood.api.domain.entity.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Restaurante toDomain(RestauranteRequestDTO dto){
        return mapper.map(dto, Restaurante.class);
    }

    public void copyToDomain(RestauranteRequestDTO dto, Restaurante restaurante){
        mapper.map(dto, restaurante);
    }
}
