package com.dino.algafood.api.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public GrupoNaoEncontradoException(String msg) {
        super(msg);
    }

    public GrupoNaoEncontradoException(Long grupoId) {
        this(String.format("Não existe um cadastro de um grupo com código %d", grupoId));
    }
}
