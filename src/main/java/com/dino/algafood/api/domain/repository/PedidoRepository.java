package com.dino.algafood.api.domain.repository;

import com.dino.algafood.api.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha join fetch r.formaPagamentos")
    List<Pedido> findAllPedidos();

}
