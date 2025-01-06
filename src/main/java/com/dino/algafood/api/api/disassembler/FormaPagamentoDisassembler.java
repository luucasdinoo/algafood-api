package com.dino.algafood.api.api.disassembler;

import com.dino.algafood.api.api.model.input.FormaPagamentoRequestDTO;
import com.dino.algafood.api.domain.model.entity.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDisassembler {

    @Autowired
    private ModelMapper mapper;

    public FormaPagamento toDomain(FormaPagamentoRequestDTO dto){
        return mapper.map(dto, FormaPagamento.class);
    }

    public void copyToDomain(FormaPagamentoRequestDTO dto, FormaPagamento formaPagamento){
        mapper.map(dto, formaPagamento);
    }
}
