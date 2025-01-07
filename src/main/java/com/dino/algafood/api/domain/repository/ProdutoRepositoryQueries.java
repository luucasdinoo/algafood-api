package com.dino.algafood.api.domain.repository;

import com.dino.algafood.api.domain.model.entity.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto fotoProduto);

    void delete(FotoProduto foto);
}
