package com.dino.algafood.api.api.disassembler;

import com.dino.algafood.api.api.model.input.GrupoRequestDTO;
import com.dino.algafood.api.domain.entity.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Grupo toDomain(GrupoRequestDTO dto){
        return mapper.map(dto, Grupo.class);
    }

    public void copyToDomain(GrupoRequestDTO dto, Grupo grupo){
        mapper.map(dto, grupo);
    }
}
