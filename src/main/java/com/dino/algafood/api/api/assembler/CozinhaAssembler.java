package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.controller.CozinhaController;
import com.dino.algafood.api.api.model.output.CozinhaResponseDTO;
import com.dino.algafood.api.domain.model.entity.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CozinhaAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaResponseDTO> {

    @Autowired
    private ModelMapper mapper;

    public CozinhaAssembler() {
        super(CozinhaController.class, CozinhaResponseDTO.class);
    }

    @Override
    public CozinhaResponseDTO toModel(Cozinha cozinha) {
        CozinhaResponseDTO dto = mapper.map(cozinha, CozinhaResponseDTO.class);

        dto.add(linkTo(methodOn(CozinhaController.class).buscar(dto.getId())).withSelfRel());
        dto.add(linkTo(CozinhaController.class).withRel(IanaLinkRelations.COLLECTION));
        return dto;
    }

}
