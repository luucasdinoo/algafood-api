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
import com.dino.algafood.api.domain.repository.PedidoRepository;
import com.dino.algafood.api.domain.repository.filter.PedidoFilter;
import com.dino.algafood.api.domain.service.PedidoService;
import com.dino.algafood.api.infrastructure.repository.PedidoSpecs;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoResumoResponseDTO> pesquisar(PedidoFilter filtro){
        List<Pedido> pedidos = null;

        if (filtro != null)
            pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));

        pedidos = pedidoRepository.findAllPedidos();
        return resumoAssembler.toCollectionDTO(pedidos);
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
