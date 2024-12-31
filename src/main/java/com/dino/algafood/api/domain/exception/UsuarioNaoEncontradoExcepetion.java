package com.dino.algafood.api.domain.exception;

public class UsuarioNaoEncontradoExcepetion extends EntidadeNaoEncontradaException{

    public UsuarioNaoEncontradoExcepetion(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoExcepetion(Long id) {
        this(String.format("Não existe um cadastro de usuario com código %d", id));
    }
}
