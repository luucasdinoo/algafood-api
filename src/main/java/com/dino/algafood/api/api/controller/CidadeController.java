package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.domain.entity.Cidade;
import com.dino.algafood.api.domain.exception.EstadoNaoEncontradoException;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<Cidade>> listar(){
        List<Cidade> cidades = cidadeService.listar();
        return ResponseEntity.ok(cidades);
    }

    @GetMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.OK)
    public  Cidade buscar(@PathVariable Long cidadeId){
        return cidadeService.buscarOuFalhar(cidadeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar (@RequestBody Cidade cidade){
        try {
            return  cidadeService.salvar(cidade);

        }catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<Cidade> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade){
        try {
            Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            Cidade cidadeSalva = cidadeService.salvar(cidadeAtual);
            return ResponseEntity.ok(cidadeSalva);

        }catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidade_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId){
        cidadeService.excluir(cidadeId);
    }

}
