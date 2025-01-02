package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.entity.Permissao;
import com.dino.algafood.api.domain.exception.PermissaoNaoEncontradaException;
import com.dino.algafood.api.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }

}