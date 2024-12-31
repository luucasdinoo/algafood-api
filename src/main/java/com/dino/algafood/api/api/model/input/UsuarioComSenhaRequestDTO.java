package com.dino.algafood.api.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaRequestDTO extends UsuarioRequestDTO {

    @NotBlank
    private String senha;

}
