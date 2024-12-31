package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.CozinhaAssembler;
import com.dino.algafood.api.api.disassembler.CozinhaDisassembler;
import com.dino.algafood.api.api.model.output.CozinhaResponseDTO;
import com.dino.algafood.api.api.model.input.CozinhaResquestDTO;
import com.dino.algafood.api.domain.entity.Cozinha;
import com.dino.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.service.CadastroCozinhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaAssembler assembler;

    @Autowired
    private CozinhaDisassembler disassembler;

    @GetMapping()
    public ResponseEntity<List<CozinhaResponseDTO>> listar(){
        List<Cozinha> cozinhas = cadastroCozinhaService.listar();
        return ResponseEntity.ok(assembler.toCollectionDTO(cozinhas));
    }

    @GetMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.OK)
    public CozinhaResponseDTO buscar(@PathVariable Long cozinhaId){
        return assembler.toDTO(cadastroCozinhaService.buscarOuFalhar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaResponseDTO adicionar(@RequestBody @Valid CozinhaResquestDTO dto){
        Cozinha cozinha = disassembler.toDomain(dto);
        return assembler.toDTO(cadastroCozinhaService.salvar(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaResponseDTO atualizar (@PathVariable Long cozinhaId, @RequestBody CozinhaResquestDTO dto){
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        disassembler.copyToDomain(dto, cozinhaAtual);
        Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual);
        return assembler.toDTO(cozinhaSalva);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId){
        cadastroCozinhaService.excluir(cozinhaId);
    }
}
