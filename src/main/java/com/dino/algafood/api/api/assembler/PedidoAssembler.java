package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.controller.PedidoController;
import com.dino.algafood.api.api.links.AlgaLinks;
import com.dino.algafood.api.api.model.output.PedidoResponseDTO;
import com.dino.algafood.api.domain.model.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResponseDTO> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoAssembler() {
        super(PedidoController.class, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO toModel(Pedido entity) {
        PedidoResponseDTO dto = mapper.map(entity, PedidoResponseDTO.class);

        dto.add(algaLinks.linkToPedido(dto.getCodigo()));

        dto.add(algaLinks.linkToPedidos());

        if (entity.podeSerConfirmado())
            dto.add(algaLinks.linkToConfirmacaoPedido(dto.getCodigo(), "confirmar"));

        if (entity.podeSerEntregue())
            dto.add(algaLinks.linkToEntregaPedido(dto.getCodigo(), "entregar"));

        if (entity.podeSerCancelado())
            dto.add(algaLinks.linkToCancelamentoPedido(dto.getCodigo(), "cancelar"));

        dto.getRestaurante().add(
                algaLinks.linkToRestaurante(dto.getRestaurante().getId()));
        dto.getCliente().add(
                algaLinks.linkToCliente(dto.getCliente().getId()));
        dto.getFormaPagamento().add(
                algaLinks.linkToFormaPagamento(dto.getFormaPagamento().getId()));
        dto.getEndereco().getCidade().add(
                algaLinks.linkToCidade(dto.getEndereco().getCidade().getId()));
        dto.getItens().forEach(item -> {
            item.add(algaLinks.linkToProduto(dto.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });
        return dto;
    }
}
