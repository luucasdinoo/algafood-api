package com.dino.algafood.api.domain.repository;

import com.dino.algafood.api.domain.model.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {}
