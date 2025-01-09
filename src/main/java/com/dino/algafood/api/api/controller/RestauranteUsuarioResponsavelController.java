package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.UsuarioAssembler;
import com.dino.algafood.api.api.model.output.UsuarioResponseDTO;
import com.dino.algafood.api.domain.model.entity.Usuario;
import com.dino.algafood.api.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioAssembler assembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UsuarioResponseDTO> listar(@PathVariable Long restauranteId) {
        List<Usuario> responsaveis = restauranteService.listarResponsaveis(restauranteId);
        return assembler.toCollectionModel(responsaveis).removeLinks();
    }

    @PutMapping("/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.associarResponsavel(restauranteId, responsavelId);
    }

    @DeleteMapping("/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.desassociarResponsavel(restauranteId, responsavelId);
    }
}
