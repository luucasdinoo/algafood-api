package com.dino.algafood.api.domain.entity;

import com.dino.algafood.api.core.validation.ValorZeroIncluiDescricao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    private Boolean ativo = Boolean.TRUE;

    private Boolean aberto = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false,  columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "restaurante_forma_pagamento",
                joinColumns = @JoinColumn(name = "restaurante_id"),
                inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
    )
    private Set<FormaPagamento> formaPagamentos = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    public void ativar(){
        setAtivo(true);
    }

    public void inativar(){
        setAtivo(false);
    }

    public void abrir(){
        setAberto(true);
    }

    public void fechar(){
        setAberto(false);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento){
        return getFormaPagamentos().remove(formaPagamento);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento){
        return getFormaPagamentos().add(formaPagamento);
    }
}
