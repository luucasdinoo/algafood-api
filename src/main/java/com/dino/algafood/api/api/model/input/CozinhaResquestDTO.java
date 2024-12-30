package com.dino.algafood.api.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaResquestDTO {

    @NotBlank
    private String nome;

}
