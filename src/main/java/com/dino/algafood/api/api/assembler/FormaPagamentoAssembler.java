package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.model.FormaPagamentoResponseDTO;
import com.dino.algafood.api.domain.entity.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoAssembler {

    @Autowired
    private ModelMapper mapper;

    public FormaPagamentoResponseDTO toDTO(FormaPagamento formaPagamento) {
        return mapper.map(formaPagamento, FormaPagamentoResponseDTO.class);
    }

    public List<FormaPagamentoResponseDTO> toCollectionDTO(List<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}