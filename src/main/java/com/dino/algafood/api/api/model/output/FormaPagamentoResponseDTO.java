package com.dino.algafood.api.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class FormaPagamentoResponseDTO {

    private Long id;
    private String descricao;
    private OffsetDateTime dataAtualizacao;

}
