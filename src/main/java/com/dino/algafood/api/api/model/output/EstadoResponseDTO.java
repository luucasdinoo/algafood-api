package com.dino.algafood.api.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Getter
@Setter
public class EstadoResponseDTO extends RepresentationModel<EstadoResponseDTO> {

    private Long id;
    private String nome;
}
