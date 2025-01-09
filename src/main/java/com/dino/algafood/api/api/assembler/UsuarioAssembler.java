package com.dino.algafood.api.api.assembler;

import com.dino.algafood.api.api.controller.UsuarioController;
import com.dino.algafood.api.api.controller.UsuarioGrupoController;
import com.dino.algafood.api.api.model.output.UsuarioResponseDTO;
import com.dino.algafood.api.domain.model.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioResponseDTO> {

    @Autowired
    private ModelMapper mapper;

    public UsuarioAssembler() {
        super(UsuarioController.class, UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO toModel(Usuario entity) {
        UsuarioResponseDTO dto =  mapper.map(entity, UsuarioResponseDTO.class);

        dto.add(linkTo(methodOn(UsuarioController.class)
                .buscar(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(UsuarioController.class)
                .listar()).withRel(IanaLinkRelations.COLLECTION));
        dto.add(linkTo(methodOn(UsuarioGrupoController.class)
                .listar(dto.getId())).withRel("grupos-usuarios"));
        return dto;
    }

    @Override
    public CollectionModel<UsuarioResponseDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }
}
