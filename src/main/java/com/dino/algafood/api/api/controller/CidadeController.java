package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.CidadeAssembler;
import com.dino.algafood.api.api.disassembler.CidadeDisassembler;
import com.dino.algafood.api.api.model.input.CidadeRequestDTO;
import com.dino.algafood.api.api.model.output.CidadeResponseDTO;
import com.dino.algafood.api.domain.entity.Cidade;
import com.dino.algafood.api.domain.exception.EstadoNaoEncontradoException;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.service.CidadeService;
import jakarta.validation.Valid;
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

    @Autowired
    private CidadeAssembler assembler;

    @Autowired
    private CidadeDisassembler disassembler;

    @GetMapping
    public ResponseEntity<List<CidadeResponseDTO>> listar(){
        List<Cidade> cidades = cidadeService.listar();
        return ResponseEntity.ok(assembler.toCollectionDTO(cidades));
    }

    @GetMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.OK)
    public CidadeResponseDTO buscar(@PathVariable Long cidadeId){
        return assembler.toDTO(cidadeService.buscarOuFalhar(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponseDTO adicionar (@RequestBody @Valid CidadeRequestDTO dto){
        Cidade cidade = disassembler.toDomain(dto);
        return  assembler.toDTO(cidadeService.salvar(cidade));
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeResponseDTO> atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeRequestDTO dto){
        try {
            Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
            disassembler.copyToDomain(dto, cidadeAtual);
            Cidade cidadeSalva = cidadeService.salvar(cidadeAtual);
            return ResponseEntity.ok(assembler.toDTO(cidadeSalva));

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
