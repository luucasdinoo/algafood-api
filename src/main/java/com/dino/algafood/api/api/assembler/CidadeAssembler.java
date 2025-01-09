package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.controller.CidadeController;
import com.dino.algafood.api.api.controller.EstadoController;
import com.dino.algafood.api.api.model.output.CidadeResponseDTO;
import com.dino.algafood.api.domain.model.entity.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeResponseDTO> {

    @Autowired
    private ModelMapper mapper;

    public CidadeAssembler() {
        super(CidadeController.class, CidadeResponseDTO.class);
    }

    @Override
    public CidadeResponseDTO toModel(Cidade entity) {
        CidadeResponseDTO dto = mapper.map(entity, CidadeResponseDTO.class);

        dto.add(linkTo(methodOn(CidadeController.class)
                .buscar(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(CidadeController.class)
                .listar()).withRel(IanaLinkRelations.COLLECTION));
        dto.add(linkTo(methodOn(EstadoController.class)
                .buscar(dto.getEstado().getId())).withSelfRel());

        return dto;
    }

    @Override
    public CollectionModel<CidadeResponseDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CidadeController.class).withSelfRel());
    }
}
