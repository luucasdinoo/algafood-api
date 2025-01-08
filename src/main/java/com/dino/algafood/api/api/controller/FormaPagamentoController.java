package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.FormaPagamentoAssembler;
import com.dino.algafood.api.api.disassembler.FormaPagamentoDisassembler;
import com.dino.algafood.api.api.model.input.FormaPagamentoRequestDTO;
import com.dino.algafood.api.api.model.output.FormaPagamentoResponseDTO;
import com.dino.algafood.api.domain.model.entity.FormaPagamento;
import com.dino.algafood.api.domain.repository.FormaPagamentoRepository;
import com.dino.algafood.api.domain.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoAssembler assembler;

    @Autowired
    private FormaPagamentoDisassembler disassembler;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FormaPagamentoResponseDTO> buscar(@PathVariable Long id){
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(id);
        FormaPagamentoResponseDTO dto =  assembler.toDTO(formaPagamento);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(20, TimeUnit.SECONDS))
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamentoResponseDTO>> listar(ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag))
            return null;

        List<FormaPagamento> formaPagamentos = formaPagamentoService.listar();
        List<FormaPagamentoResponseDTO> dto = assembler.toCollectionDTO(formaPagamentos);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(dto);
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
