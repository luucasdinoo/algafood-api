package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.core.validation.ValidacaoException;
import com.dino.algafood.api.domain.entity.Restaurante;
import com.dino.algafood.api.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        List<Restaurante> restaurantes = restauranteService.listar();
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.OK)
    public Restaurante buscar(@PathVariable Long restauranteId) {
        return restauranteService.buscarOuFalhar(restauranteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
        return restauranteService.salvar(restaurante);
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@RequestBody @Valid Restaurante restaurante, @PathVariable Long restauranteId) {
        Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formaPagamentos", "endereco", "dataCadastro");
        return restauranteService.salvar(restauranteAtual);
    }

}
