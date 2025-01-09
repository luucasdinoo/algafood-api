package com.dino.algafood.api.api.model.output;

import com.dino.algafood.api.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteResponseDTO extends RepresentationModel<RestauranteResponseDTO> {

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNomes.class})
    private Long id;

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNomes.class})
    private String nome;

    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaResponseDTO cozinha;

    private Boolean ativo;
    private Boolean aberto;
    private EnderecoResponseDTO endereco;

}
