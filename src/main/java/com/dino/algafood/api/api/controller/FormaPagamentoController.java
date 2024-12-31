package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.FormaPagamentoAssembler;
import com.dino.algafood.api.api.disassembler.FormaPagamentoDisassembler;
import com.dino.algafood.api.api.model.output.FormaPagamentoResponseDTO;
import com.dino.algafood.api.api.model.input.FormaPagamentoRequestDTO;
import com.dino.algafood.api.domain.entity.FormaPagamento;
import com.dino.algafood.api.domain.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoAssembler assembler;

    @Autowired
    private FormaPagamentoDisassembler disassembler;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FormaPagamentoResponseDTO buscar(@PathVariable Long id){
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(id);
        return assembler.toDTO(formaPagamento);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FormaPagamentoResponseDTO> listar(){
        List<FormaPagamento> formaPagamentos = formaPagamentoService.listar();
        return assembler.toCollectionDTO(formaPagamentos);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoResponseDTO salvar(@RequestBody @Valid FormaPagamentoRequestDTO request){
        FormaPagamento formaPagamento = disassembler.toDomain(request);
        return assembler.toDTO(formaPagamentoService.salvar(formaPagamento));
    }

    @PutMapping("/{id}")
    public FormaPagamentoResponseDTO atualizar(
            @RequestBody @Valid FormaPagamentoRequestDTO request, @PathVariable Long id){

        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(id);
        disassembler.copyToDomain(request, formaPagamento);
        FormaPagamento formaPagamentoSalvo = formaPagamentoService.salvar(formaPagamento);
        return assembler.toDTO(formaPagamentoSalvo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        formaPagamentoService.excluir(id);
    }
}
