package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.domain.entity.Cozinha;
import com.dino.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
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

    @GetMapping()
    public ResponseEntity<List<Cozinha>> listar(){
        List<Cozinha> cozinhas = cadastroCozinhaService.listar();
        return ResponseEntity.ok(cozinhas);
    }

    @GetMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.OK)
    public Cozinha buscar(@PathVariable Long cozinhaId){
        return cadastroCozinhaService.buscarOuFalhar(cozinhaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        try {
            return cadastroCozinhaService.salvar(cozinha);

        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{cozinhaId}")
    public Cozinha atualizar (@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha){
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        BeanUtils.copyProperties(cozinha, cozinhaAtual,"id");
        try {
            return cadastroCozinhaService.salvar(cozinhaAtual);

        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId){
        cadastroCozinhaService.excluir(cozinhaId);
    }
}
