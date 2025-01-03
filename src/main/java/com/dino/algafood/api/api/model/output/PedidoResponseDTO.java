package com.dino.algafood.api.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDTO {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private RestauranteResumoResponseDTO restaurante;
    private UsuarioResponseDTO usuario;
    private FormaPagamentoResponseDTO pagamento;
    private EnderecoResponseDTO endereco;
    private List<ItemPedidoResponseDTO> itens;
}
