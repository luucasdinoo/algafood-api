package com.dino.algafood.api.api.model.mixin;

import com.dino.algafood.api.domain.entity.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
