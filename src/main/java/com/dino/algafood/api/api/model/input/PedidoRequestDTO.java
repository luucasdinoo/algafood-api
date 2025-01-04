package com.dino.algafood.api.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequestDTO {

    @Valid
    @NotNull
    private RestauranteIdRequestDTO restaurante;

    @Valid
    @NotNull
    private FormaPagamentoIdRequestDTO formaPagamento;

    @Valid
    @NotNull
    private EnderecoRequestDTO enderecoEntrega;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ItemPedidoRequestDTO> itens;
}
