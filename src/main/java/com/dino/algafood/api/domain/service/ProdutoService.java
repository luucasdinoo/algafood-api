package com.dino.algafood.api.domain.service;

import com.dino.algafood.api.domain.exception.ProdutoNaoEncontradoException;
import com.dino.algafood.api.domain.model.entity.Produto;
import com.dino.algafood.api.domain.model.entity.Restaurante;
import com.dino.algafood.api.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Transactional
    public Produto salvar(Produto produto, Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        produto.setRestaurante(restaurante);

        return produtoRepository.save(produto);
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }
}
