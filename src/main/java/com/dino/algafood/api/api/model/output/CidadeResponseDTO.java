package com.dino.algafood.api.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResponseDTO {

    private Long id;
    private String nome;
    private EstadoResponseDTO estado;

}
