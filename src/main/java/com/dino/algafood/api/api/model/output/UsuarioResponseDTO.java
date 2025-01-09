package com.dino.algafood.api.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
public class UsuarioResponseDTO extends RepresentationModel<UsuarioResponseDTO> {

    private Long id;
    private String nome;
    private String email;

}
