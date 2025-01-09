package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.controller.PedidoController;
import com.dino.algafood.api.api.controller.RestauranteController;
import com.dino.algafood.api.api.controller.UsuarioController;
import com.dino.algafood.api.api.model.output.PedidoResumoResponseDTO;
import com.dino.algafood.api.domain.model.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoResponseDTO> {

    @Autowired
    private ModelMapper mapper;

    public PedidoResumoAssembler() {
        super(PedidoController.class, PedidoResumoResponseDTO.class);
    }

    @Override
    public PedidoResumoResponseDTO toModel(Pedido entity) {
        PedidoResumoResponseDTO dto =  mapper.map(entity, PedidoResumoResponseDTO.class);

        dto.add(linkTo(methodOn(PedidoController.class)
                .buscar(dto.getCodigo())).withSelfRel());
        dto.add(linkTo(PedidoController.class)
                .withRel(IanaLinkRelations.COLLECTION));
        dto.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(dto.getRestaurante().getId())).withSelfRel());
        dto.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(dto.getCliente().getId())).withSelfRel());

        return dto;
    }
}
