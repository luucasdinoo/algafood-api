package com.dino.algafood.api.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(Long pedidoId) {
        this(String.format("Não existe um cadastro de pedido com código %", pedidoId));
    }
}
