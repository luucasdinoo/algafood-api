package com.dino.algafood.api.api.disassembler;

import com.dino.algafood.api.api.model.input.PedidoRequestDTO;
import com.dino.algafood.api.domain.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Pedido toDomain(PedidoRequestDTO dto){
        return mapper.map(dto, Pedido.class);
    }

    public void copyToDomain(PedidoRequestDTO dto, Pedido pedido){
        mapper.map(dto, pedido);
    }
}
