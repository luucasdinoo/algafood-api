package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.RestauranteResponseDTO;
import com.dino.algafood.api.domain.model.entity.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteAssembler {

    @Autowired
    private ModelMapper mapper;

    public RestauranteResponseDTO toDto(Restaurante restaurante) {
        return mapper.map(restaurante, RestauranteResponseDTO.class);
    }

    public List<RestauranteResponseDTO> toCollectionDto(List<Restaurante> restaurantes){
        return restaurantes.stream().map(this::toDto)
                .collect(Collectors.toList());
    }
}
