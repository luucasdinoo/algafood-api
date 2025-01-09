package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.CidadeAssembler;
import com.dino.algafood.api.api.disassembler.CidadeDisassembler;
import com.dino.algafood.api.api.model.input.CidadeRequestDTO;
import com.dino.algafood.api.api.model.output.CidadeResponseDTO;
import com.dino.algafood.api.api.util.ResourceUriHelper;
import com.dino.algafood.api.domain.exception.EstadoNaoEncontradoException;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.model.entity.Cidade;
import com.dino.algafood.api.domain.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

    @Autowired
    private CidadeAssembler assembler;

    @Autowired
    private CidadeDisassembler disassembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CidadeResponseDTO> listar(){
        List<Cidade> cidades = cidadeService.listar();
        return assembler.toCollectionModel(cidades);
    }

    @GetMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.OK)
    public CidadeResponseDTO buscar(@PathVariable Long cidadeId){
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
        return assembler.toModel(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponseDTO adicionar (@RequestBody @Valid CidadeRequestDTO dto){
        try {
            Cidade cidade = disassembler.toDomain(dto);
            cidade = cidadeService.salvar(cidade);
            CidadeResponseDTO responseDTO = assembler.toModel(cidade);

            ResourceUriHelper.addUriInResponseHeader(responseDTO.getId());
            return responseDTO;

        }catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeResponseDTO> atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeRequestDTO dto){
        try {
            Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
            disassembler.copyToDomain(dto, cidadeAtual);
            Cidade cidadeSalva = cidadeService.salvar(cidadeAtual);
            return ResponseEntity.ok(assembler.toModel(cidadeSalva));

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
