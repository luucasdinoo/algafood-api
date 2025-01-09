package com.dino.algafood.api.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.OffsetDateTime;


@Getter
@Setter
public class FormaPagamentoResponseDTO extends RepresentationModel<FormaPagamentoResponseDTO> {

    private Long id;
    private String descricao;
    private OffsetDateTime dataAtualizacao;

}
