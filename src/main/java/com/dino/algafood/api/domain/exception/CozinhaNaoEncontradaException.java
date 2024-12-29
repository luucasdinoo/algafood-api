package com.dino.algafood.api.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradaException(String msg) {
        super(msg);
    }

    public CozinhaNaoEncontradaException(Long cozinhaId) {
        this(String.format("\"Não existe um cadastro de cozinha com código %d\"", cozinhaId));
    }
}
