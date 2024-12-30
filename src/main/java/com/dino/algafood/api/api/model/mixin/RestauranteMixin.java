package com.dino.algafood.api.api.model.mixin;

import com.dino.algafood.api.domain.entity.Cozinha;
import com.dino.algafood.api.domain.entity.Endereco;
import com.dino.algafood.api.domain.entity.FormaPagamento;
import com.dino.algafood.api.domain.entity.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    private List<FormaPagamento> formaPagamentos = new ArrayList<>();

    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();
}
