package com.dino.algafood.api.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(String msg) {
        super(msg);
    }

    public RestauranteNaoEncontradoException(Long restauranteId) {
        this(String.format("\"Não existe um cadastro de restaurante com código %d\"", restauranteId));
    }
}
