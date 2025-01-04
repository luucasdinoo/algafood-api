package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.PedidoAssembler;
import com.dino.algafood.api.api.assembler.PedidoResumoAssembler;
import com.dino.algafood.api.api.disassembler.PedidoDisassembler;
import com.dino.algafood.api.api.model.input.PedidoRequestDTO;
import com.dino.algafood.api.api.model.output.PedidoResponseDTO;
import com.dino.algafood.api.api.model.output.PedidoResumoResponseDTO;
import com.dino.algafood.api.domain.entity.Pedido;
import com.dino.algafood.api.domain.entity.Usuario;
import com.dino.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.service.PedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoAssembler assembler;

    @Autowired
    private PedidoDisassembler disassembler;

    @Autowired
    private PedidoResumoAssembler resumoAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MappingJacksonValue listar(@RequestParam(required = false) String campos){
        List<Pedido> pedidos = pedidoService.listar();
        List<PedidoResumoResponseDTO> pedidosDtos = resumoAssembler.toCollectionDTO(pedidos);

        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosDtos);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

        if (StringUtils.isNotBlank(campos))
            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));

        pedidosWrapper.setFilters(filterProvider);

        return pedidosWrapper;
    }

    @GetMapping("/{codigoPedido}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoResponseDTO buscar(@PathVariable String codigoPedido){
        Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
        return assembler.toDTO(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO emitir(@RequestBody @Valid PedidoRequestDTO dto){
        try {
            Pedido novoPedido = disassembler.toDomain(dto);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = pedidoService.emitir(novoPedido);
            return assembler.toDTO(novoPedido);

        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
