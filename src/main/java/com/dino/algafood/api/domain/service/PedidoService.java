package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.entity.Pedido;
import com.dino.algafood.api.domain.exception.PedidoNaoEncontradoException;
import com.dino.algafood.api.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public Pedido buscarOuFalhar(Long pedidoId) {
       Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
       pedido.getItens().size();
       return pedido;
    }

    @Transactional
    public List<Pedido> listar() {
        List<Pedido> pedidos = pedidoRepository.findAllPedidos();
        return pedidos;
    }
}
