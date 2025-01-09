package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.PedidoAssembler;
import com.dino.algafood.api.api.assembler.PedidoResumoAssembler;
import com.dino.algafood.api.api.disassembler.PedidoDisassembler;
import com.dino.algafood.api.api.model.input.PedidoRequestDTO;
import com.dino.algafood.api.api.model.output.PedidoResponseDTO;
import com.dino.algafood.api.api.model.output.PedidoResumoResponseDTO;
import com.dino.algafood.api.core.data.PageWrapper;
import com.dino.algafood.api.core.data.PageableTranslator;
import com.dino.algafood.api.domain.exception.EntidadeNaoEncontradaException;
import com.dino.algafood.api.domain.exception.NegocioException;
import com.dino.algafood.api.domain.filter.PedidoFilter;
import com.dino.algafood.api.domain.model.entity.Pedido;
import com.dino.algafood.api.domain.model.entity.Usuario;
import com.dino.algafood.api.domain.repository.PedidoRepository;
import com.dino.algafood.api.domain.service.PedidoService;
import com.dino.algafood.api.infrastructure.repository.specs.PedidoSpecs;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoAssembler assembler;

    @Autowired
    private PedidoDisassembler disassembler;

    @Autowired
    private PedidoResumoAssembler resumoAssembler;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<PedidoResumoResponseDTO> pesquisar(PedidoFilter filtro, Pageable pageable){
        Pageable pageableTraduzido = traduzirPageable(pageable);
        Page<Pedido> pedidosPage = null;

        if (filtro != null) {
            pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);
            pedidosPage = new PageWrapper<>(pedidosPage, pageable);
            return pagedResourcesAssembler.toModel(pedidosPage, resumoAssembler);
        }

        pedidosPage = pedidoRepository.findAllPedidos(pageable);
        return pagedResourcesAssembler.toModel(pedidosPage, resumoAssembler);
    }

    @GetMapping("/{codigoPedido}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoResponseDTO buscar(@PathVariable String codigoPedido){
        Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
        return assembler.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO emitir(@RequestBody @Valid PedidoRequestDTO dto){
        try {
            Pedido novoPedido = disassembler.toDomain(dto);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = pedidoService.emitir(novoPedido);
            return assembler.toModel(novoPedido);

        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "restauranteNome", "restaurante.nome",
                "restauranteId", "restaurante.id",
                "clienteId", "cliente.id",
                "clienteNome", "cliente.nome"
        );

        return PageableTranslator.translate(pageable, mapeamento);
    }
}
