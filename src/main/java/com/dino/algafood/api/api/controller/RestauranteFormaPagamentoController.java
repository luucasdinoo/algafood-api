package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.FormaPagamentoAssembler;
import com.dino.algafood.api.api.model.output.FormaPagamentoResponseDTO;
import com.dino.algafood.api.domain.entity.Restaurante;
import com.dino.algafood.api.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoAssembler assembler;

    @GetMapping
    public List<FormaPagamentoResponseDTO> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return assembler.toCollectionDTO(restaurante.getFormaPagamentos());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociar(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associar(restauranteId, formaPagamentoId);
    }

}
