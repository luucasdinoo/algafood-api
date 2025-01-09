package com.dino.algafood.api.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoResponseDTO{

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private CidadeResumoDTO cidade;
}
