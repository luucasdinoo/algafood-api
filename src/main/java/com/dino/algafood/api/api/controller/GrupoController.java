package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.GrupoAssembler;
import com.dino.algafood.api.api.disassembler.GrupoDisassembler;
import com.dino.algafood.api.api.model.input.GrupoRequestDTO;
import com.dino.algafood.api.api.model.output.GrupoResponseDTO;
import com.dino.algafood.api.domain.model.entity.Grupo;
import com.dino.algafood.api.domain.service.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoAssembler assembler;

    @Autowired
    private GrupoDisassembler disassembler;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GrupoResponseDTO buscar(@PathVariable Long id) {
        Grupo response = grupoService.buscarOuFalhar(id);
        return assembler.toDTO(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GrupoResponseDTO> listar() {
        List<Grupo> response = grupoService.listar();
        return assembler.toCollectionDTO(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoResponseDTO salvar(@Valid @RequestBody GrupoRequestDTO request) {
        Grupo grupo = disassembler.toDomain(request);
        return assembler.toDTO(grupoService.salvar(grupo));
    }

    @PutMapping("/{id}")
    public GrupoResponseDTO atualizar(@Valid @RequestBody GrupoRequestDTO request, @PathVariable Long id) {
        Grupo grupo = grupoService.buscarOuFalhar(id);
        disassembler.copyToDomain(request, grupo);
        Grupo response = grupoService.salvar(grupo);
        return assembler.toDTO(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        grupoService.excluir(id);
    }

}
