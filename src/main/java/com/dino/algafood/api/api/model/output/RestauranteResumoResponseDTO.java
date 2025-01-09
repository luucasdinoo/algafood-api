package com.dino.algafood.api.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class RestauranteResumoResponseDTO extends RepresentationModel<RestauranteResumoResponseDTO> {
    private Long id;
    private String nome;
}
