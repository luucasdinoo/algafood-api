package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.api.assembler.PedidoAssembler;
import com.dino.algafood.api.api.assembler.PedidoResumoAssembler;
import com.dino.algafood.api.api.model.output.PedidoResponseDTO;
import com.dino.algafood.api.api.model.output.PedidoResumoResponseDTO;
import com.dino.algafood.api.domain.entity.Pedido;
import com.dino.algafood.api.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private PedidoResumoAssembler resumoAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoResumoResponseDTO> listar(){
        List<Pedido> pedidos = pedidoService.listar();
        return resumoAssembler.toCollectionDTO(pedidos);
    }

    @GetMapping("/{pedidoId}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoResponseDTO buscar(@PathVariable Long pedidoId){
        Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
        return assembler.toDTO(pedido);
    }
}
