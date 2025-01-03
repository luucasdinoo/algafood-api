package com.dino.algafood.api.domain.repository;

import com.dino.algafood.api.domain.entity.FormaPagamento;
import com.dino.algafood.api.domain.entity.Restaurante;
import com.dino.algafood.api.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    @Query("select r.responsaveis from Restaurante r where r.id = :restauranteId")
    List<Usuario> findResponsaveisByRestauranteId(@Param("restauranteId") Long restauranteId);

    @Query("select r.formaPagamentos from Restaurante r where r.id = :restauranteId")
    List<FormaPagamento> findFormaPagamentosByRestauranteId(Long restauranteId);

}
