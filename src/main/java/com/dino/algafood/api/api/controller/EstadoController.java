package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.EstadoAssembler;
import com.dino.algafood.api.api.disassembler.EstadoDisassembler;
import com.dino.algafood.api.api.model.input.EstadoRequestDTO;
import com.dino.algafood.api.api.model.output.EstadoResponseDTO;
import com.dino.algafood.api.domain.model.entity.Estado;
import com.dino.algafood.api.domain.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoAssembler assembler;

    @Autowired
    private EstadoDisassembler disassembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EstadoResponseDTO>> listar(){
        List<Estado> estados = estadoService.listar();
        return ResponseEntity.ok(assembler.toCollectionModel(estados));
    }

    @GetMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoResponseDTO buscar(@PathVariable Long estadoId){
        return assembler.toModel(estadoService.buscarOuFalhar(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoResponseDTO adicionar(@RequestBody @Valid EstadoRequestDTO dto){
        Estado estado = disassembler.toDomain(dto);
        return assembler.toModel(estadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoResponseDTO> atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoRequestDTO dto){
        Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);
        disassembler.copyToDomain(dto, estadoAtual);
        Estado estadoSalvo = estadoService.salvar(estadoAtual);
        return ResponseEntity.ok(assembler.toModel(estadoSalvo));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.excluir(estadoId);
    }
}
