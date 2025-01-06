package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.PedidoResponseDTO;
import com.dino.algafood.api.domain.model.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoAssembler {

    @Autowired
    private ModelMapper mapper;

    public PedidoResponseDTO toDTO(Pedido pedido) {
        return mapper.map(pedido, PedidoResponseDTO.class);
    }

    public List<PedidoResponseDTO> toCollectionDTO(List<Pedido> pedidos){
        return pedidos.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
