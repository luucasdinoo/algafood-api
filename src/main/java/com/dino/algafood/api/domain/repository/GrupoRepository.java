package com.dino.algafood.api.domain.repository;

import com.dino.algafood.api.domain.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {}
