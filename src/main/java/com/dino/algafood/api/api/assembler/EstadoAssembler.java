package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.controller.EstadoController;
import com.dino.algafood.api.api.model.output.EstadoResponseDTO;
import com.dino.algafood.api.domain.model.entity.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstadoAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoResponseDTO> {

    @Autowired
    private ModelMapper mapper;

    public EstadoAssembler() {
        super(EstadoController.class, EstadoResponseDTO.class);
    }

    @Override
    public EstadoResponseDTO toModel(Estado entity) {
        EstadoResponseDTO dto =  mapper.map(entity, EstadoResponseDTO.class);

        dto.add(linkTo(methodOn(EstadoController.class)
                .buscar(dto.getId())).withSelfRel());
        dto.add(linkTo(EstadoController.class).withRel(IanaLinkRelations.COLLECTION));
        return dto;
    }

    @Override
    public CollectionModel<EstadoResponseDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(EstadoController.class).withRel("estados"));
    }
}
