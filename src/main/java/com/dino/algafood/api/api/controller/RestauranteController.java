package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.RestauranteAssembler;
import com.dino.algafood.api.api.disassembler.RestauranteDisassembler;
import com.dino.algafood.api.api.model.input.RestauranteRequestDTO;
import com.dino.algafood.api.api.model.output.RestauranteResponseDTO;
import com.dino.algafood.api.api.model.view.RestauranteView;
import com.dino.algafood.api.domain.entity.Restaurante;
import com.dino.algafood.api.domain.exception.CidadeNaoEncontradaException;
import com.dino.algafood.api.domain.exception.CozinhaNaoEncontradaException;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.exception.RestauranteNaoEncontradoException;
import com.dino.algafood.api.domain.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteAssembler assembler;

    @Autowired
    private RestauranteDisassembler disassembler;

    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
        List<Restaurante> restaurantes = restauranteService.listar();
        List<RestauranteResponseDTO> restaurantesDto = assembler.toCollectionDto(restaurantes);

        MappingJacksonValue restauranteWrapper = new MappingJacksonValue(restaurantesDto);

        restauranteWrapper.setSerializationView(RestauranteView.Resumo.class);

        if ("nomes".equals(projecao))
            restauranteWrapper.setSerializationView(RestauranteView.ApenasNomes.class);

        if("completo".equals(projecao))
            restauranteWrapper.setSerializationView(null);

        return restauranteWrapper;
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

        }catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
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
        }catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.ativar(restauranteIds);

        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.inativar(restauranteIds);

        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
    }
}
