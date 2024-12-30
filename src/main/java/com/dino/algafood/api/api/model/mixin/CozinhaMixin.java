package com.dino.algafood.api.api.model.mixin;

import com.dino.algafood.api.domain.entity.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes = new ArrayList<>();

}
