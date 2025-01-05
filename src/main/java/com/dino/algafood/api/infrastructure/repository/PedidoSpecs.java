package com.dino.algafood.api.infrastructure.repository;

import com.dino.algafood.api.domain.entity.Pedido;
import com.dino.algafood.api.domain.repository.filter.PedidoFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, builder) -> {
            if (Pedido.class.equals(query.getResultType())) {
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
                root.fetch("formaPagamento");

            }
            var predicates = new ArrayList<Predicate>();

            if (filtro.getClienteId() != null)
                predicates.add(builder.equal(root.get("cliente").get("id"), filtro.getClienteId()));

            if (filtro.getRestauranteId() != null)
                predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));

            if (filtro.getDataCriacaoInicio() != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));

            if (filtro.getDataCriacaoFim() != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));


            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
