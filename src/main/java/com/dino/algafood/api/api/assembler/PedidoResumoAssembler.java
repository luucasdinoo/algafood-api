package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.output.PedidoResumoResponseDTO;
import com.dino.algafood.api.domain.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoAssembler {

    @Autowired
    private ModelMapper mapper;

    public PedidoResumoResponseDTO toDTO(Pedido pedido) {
        return mapper.map(pedido, PedidoResumoResponseDTO.class);
    }

    public List<PedidoResumoResponseDTO> toCollectionDTO(List<Pedido> pedidos){
        return pedidos.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
