package com.dino.algafood.api.domain.repository;

import com.dino.algafood.api.domain.model.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {}
