package com.dino.algafood.api.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaResquestDTO {

    @NotBlank
    private String nome;

}
