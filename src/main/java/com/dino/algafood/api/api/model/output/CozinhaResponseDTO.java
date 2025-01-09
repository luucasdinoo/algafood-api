package com.dino.algafood.api.api.model.output;

import com.dino.algafood.api.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaResponseDTO extends RepresentationModel<CozinhaResponseDTO> {

    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @JsonView(RestauranteView.Resumo.class)
    private String nome;

}
