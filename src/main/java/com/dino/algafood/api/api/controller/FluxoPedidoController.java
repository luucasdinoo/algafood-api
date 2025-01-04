package com.dino.algafood.api.api.controller;

import com.dino.algafood.api.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class FluxoPedidoController {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido) {
        fluxoPedidoService.confirmar(codigoPedido);
    }

    @PutMapping("/entregue")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregue(@PathVariable String codigoPedido) {
        fluxoPedidoService.entregue(codigoPedido);
    }

    @PutMapping("/cancelado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido) {
        fluxoPedidoService.cancelar(codigoPedido);
    }
}
