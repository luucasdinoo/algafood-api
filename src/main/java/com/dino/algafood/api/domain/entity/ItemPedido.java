package com.dino.algafood.api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
public class ItemPedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, name = "preco_unitario")
    private BigDecimal precoUnitario;

    @Column(nullable = false, name = "preco_total")
    private BigDecimal precoTotal;

    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false, name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(nullable = false, name = "produto_id")
    private Produto produto;

}
