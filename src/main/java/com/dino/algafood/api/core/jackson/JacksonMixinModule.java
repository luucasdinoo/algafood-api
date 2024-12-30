package com.dino.algafood.api.core.jackson;

import com.dino.algafood.api.api.model.mixin.CidadeMixin;
import com.dino.algafood.api.api.model.mixin.CozinhaMixin;
import com.dino.algafood.api.api.model.mixin.RestauranteMixin;
import com.dino.algafood.api.domain.entity.Cidade;
import com.dino.algafood.api.domain.entity.Cozinha;
import com.dino.algafood.api.domain.entity.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule(){
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }
}
