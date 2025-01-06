package com.dino.algafood.api.domain.repository;

import com.dino.algafood.api.domain.model.entity.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {}
