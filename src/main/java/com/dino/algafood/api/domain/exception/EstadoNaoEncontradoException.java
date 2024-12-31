package com.dino.algafood.api.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public EstadoNaoEncontradoException(String msg) {
        super(msg);
    }

    public EstadoNaoEncontradoException(Long estadoId) {
        this(String.format("Não existe um cadastro de estado com código %d", estadoId));
    }
}
