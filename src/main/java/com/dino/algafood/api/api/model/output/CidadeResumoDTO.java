package com.dino.algafood.api.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class CidadeResumoDTO extends RepresentationModel<CidadeResumoDTO> {

    private Long id;
    private String nome;

    //private String nomeEstado;
    private String estado;

}
