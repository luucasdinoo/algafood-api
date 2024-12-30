package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.RestauranteAssembler;
import com.dino.algafood.api.api.assembler.RestauranteDisassembler;
import com.dino.algafood.api.api.model.RestauranteResponseDTO;
import com.dino.algafood.api.api.model.input.RestauranteRequestDTO;
import com.dino.algafood.api.domain.entity.Restaurante;
import com.dino.algafood.api.domain.exception.CozinhaNaoEncontradaException;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteAssembler assembler;

    @Autowired
    private RestauranteDisassembler disassembler;

    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listar() {
        List<Restaurante> restaurantes = restauranteService.listar();
        return ResponseEntity.ok(assembler.toCollectionDto(restaurantes));
    }

    @GetMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteResponseDTO buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return assembler.toDto(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteResponseDTO adicionar(@RequestBody @Valid RestauranteRequestDTO request) {
        try {
            Restaurante restaurante = disassembler.toDomain(request);
            return assembler.toDto(restauranteService.salvar(restaurante));

        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteResponseDTO atualizar(@RequestBody @Valid RestauranteRequestDTO request, @PathVariable Long restauranteId) {
        try {
            Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
            disassembler.copyToDomain(request, restauranteAtual);
            Restaurante restauranteSalvo = restauranteService.salvar(restauranteAtual);
            return assembler.toDto(restauranteSalvo);
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }
}
