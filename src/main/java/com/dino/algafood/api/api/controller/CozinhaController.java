package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.CozinhaAssembler;
import com.dino.algafood.api.api.disassembler.CozinhaDisassembler;
import com.dino.algafood.api.api.model.input.CozinhaResquestDTO;
import com.dino.algafood.api.api.model.output.CozinhaResponseDTO;
import com.dino.algafood.api.domain.model.entity.Cozinha;
import com.dino.algafood.api.domain.repository.CozinhaRepository;
import com.dino.algafood.api.domain.service.CadastroCozinhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaAssembler assembler;

    @Autowired
    private CozinhaDisassembler disassembler;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<CozinhaResponseDTO> listar(@PageableDefault(size = 12) Pageable pageable){
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(cozinhasPage, assembler);
    }

    @GetMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.OK)
    public CozinhaResponseDTO buscar(@PathVariable Long cozinhaId){
        return assembler.toModel(cadastroCozinhaService.buscarOuFalhar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaResponseDTO adicionar(@RequestBody @Valid CozinhaResquestDTO dto){
        Cozinha cozinha = disassembler.toDomain(dto);
        return assembler.toModel(cadastroCozinhaService.salvar(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaResponseDTO atualizar (@PathVariable Long cozinhaId, @RequestBody CozinhaResquestDTO dto){
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        disassembler.copyToDomain(dto, cozinhaAtual);
        Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual);
        return assembler.toModel(cozinhaSalva);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId){
        cadastroCozinhaService.excluir(cozinhaId);
    }
}
