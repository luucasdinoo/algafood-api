package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.EstadoAssembler;
import com.dino.algafood.api.api.assembler.EstadoDisassembler;
import com.dino.algafood.api.api.model.EstadoResponseDTO;
import com.dino.algafood.api.api.model.input.EstadoRequestDTO;
import com.dino.algafood.api.domain.entity.Estado;
import com.dino.algafood.api.domain.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<EstadoResponseDTO>> listar(){
        List<Estado> estados = estadoService.listar();
        return ResponseEntity.ok(assembler.toCollectionDTO(estados));
    }

    @GetMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoResponseDTO buscar(@PathVariable Long estadoId){
        return assembler.toDTO(estadoService.buscarOuFalhar(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoResponseDTO adicionar(@RequestBody @Valid EstadoRequestDTO dto){
        Estado estado = disassembler.toDomain(dto);
        return assembler.toDTO(estadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoResponseDTO> atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoRequestDTO dto){
        Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);
        disassembler.copyToDomain(dto, estadoAtual);
        Estado estadoSalvo = estadoService.salvar(estadoAtual);
        return ResponseEntity.ok(assembler.toDTO(estadoSalvo));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.excluir(estadoId);
    }
}
