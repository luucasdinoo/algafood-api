package com.dino.algafood.api.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(String msg) {
        super(msg);
    }

    public ProdutoNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de produto com código %d", id));
    }
}
