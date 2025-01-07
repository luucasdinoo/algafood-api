package com.dino.algafood.api.domain.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class FotoProduto {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "produto_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produto;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    private String descricao;

    @Column(name = "content_type")
    private String contentType;

    private Long tamanho;

    public Long getRestauranteId(){
        if (getProduto() != null)
            return getProduto().getRestaurante().getId();

        return null;
    }
}
