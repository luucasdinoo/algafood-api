package com.dino.algafood.api.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeResponseDTO extends RepresentationModel<CidadeResponseDTO> {

    private Long id;
    private String nome;
    private EstadoResponseDTO estado;

}
